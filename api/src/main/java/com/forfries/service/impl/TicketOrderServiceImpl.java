package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.Impl.PageableServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.constant.TimeConstant;
import com.forfries.context.BaseContext;
import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.Schedule;
import com.forfries.entity.Seat;
import com.forfries.entity.Ticket;
import com.forfries.entity.TicketOrder;
import com.forfries.exception.SeatOccupiedException;
import com.forfries.exception.StandardizationErrorException;
import com.forfries.exception.SystemException;
import com.forfries.mapper.TicketOrderMapper;
import com.forfries.result.Result;
import com.forfries.service.*;
import com.forfries.vo.ScheduleSeatVO;
import com.forfries.vo.SeatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
* @author Nolan
* @description 针对表【ticket_order】的数据库操作Service实现
* @createDate 2024-10-30 15:37:57
*/
@Service
public class TicketOrderServiceImpl extends PageableServiceImpl<TicketOrderMapper, TicketOrder, TicketOrderPageDTO>
        implements TicketOrderService {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ScreeningHallService screeningHallService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TaskScheduler taskScheduler;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private static final Random random = new Random();

    public static String generateOrderNumber() {
        // 获取当前时间戳
        String timestamp = LocalDateTime.now().format(formatter);
        // 生成一个4位随机数
        int randomNum = 1000 + random.nextInt(9000);
        return timestamp + randomNum;
    }
    @Override
    protected void buildQueryWrapper(QueryWrapper<TicketOrder> queryWrapper, TicketOrderPageDTO ticketOrderPageDTO) {
        String orderNum = ticketOrderPageDTO.getOrderNum();
        if (orderNum != null && !orderNum.isEmpty()) {
            queryWrapper.like("orderNum", orderNum);
        }
        Long scheduleId = ticketOrderPageDTO.getScheduleId();
        if (scheduleId != null ) {
            queryWrapper.eq("scheduleId", scheduleId);
        }
        Long userId = ticketOrderPageDTO.getUserId();
        if (userId != null ) {
            queryWrapper.eq("userId", userId);
        }
        queryWrapper.eq("cinema_id", ticketOrderPageDTO.getCinemaId());
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }

    @Override
    public boolean createTicketOrder(TicketOrderGenerationDTO ticketOrderGenerationDTO) {
        //管理员添加订单，这里的userId为管理员
        //TODO 管理员当然不用考虑锁的事情，但是用户需要
        long userId = Long.parseLong(BaseContext.getCurrentPayload().get("userId"));
        List<Long> seatIds = ticketOrderGenerationDTO.getSeatIds();
        if(seatIds == null || seatIds.isEmpty()){
            throw new StandardizationErrorException(MessageConstant.STANDARDIZATION_ERROR);
        }

        this.checkSeatMaintenance(seatIds);
        this.checkSeatOccupied(seatIds, ticketOrderGenerationDTO.getScheduleId());

        int ticketCount = seatIds.size();
        //设置状态
        //TODO 这里可能要设置不同的状态哦 目前是只要创建订单 订单就为Normal 票为Normal
        BigDecimal price4One = scheduleService.getById(ticketOrderGenerationDTO.getScheduleId()).getTicketPrice();
        TicketOrder ticketOrder = TicketOrder.builder()
                .userId(userId)
                .orderNum(generateOrderNumber())
                .totalPrice(price4One.multiply(BigDecimal.valueOf(ticketCount)))
                .status(StatusConstant.NORMAL)
                .build();

        this.save(ticketOrder);

        Long orderId = ticketOrder.getId();
        if(orderId == null){
            throw new SystemException(MessageConstant.SYSTEM_ERROR);
        }

        long scheduleId = ticketOrderGenerationDTO.getScheduleId();
        List<Ticket> tickets = new ArrayList<>();
        for (Long seatId : ticketOrderGenerationDTO.getSeatIds()) {
            Ticket ticket = Ticket.builder()
                    .seatId(seatId)
                    .scheduleId(scheduleId)
                    .orderId(orderId)
                    .status(StatusConstant.NORMAL)
                    .build();
            tickets.add(ticket);
        }

        ticketService.saveBatch(tickets);

        return true;
    }

    private boolean checkSeatMaintenance(List<Long> seatIds) {
        if(seatService.checkSeatStatus(seatIds))
            throw new SeatOccupiedException(MessageConstant.SEAT_IN_MAINTENANCE);

        return true;
    }


    @Override
    public boolean checkSeatOccupied(List<Long> seatIds,Long scheduleId) {

        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("seat_id", seatIds)
                .eq("schedule_id", scheduleId)
                .ne("status", StatusConstant.REFUNDED)
                .ne("status",StatusConstant.CANCELED);

        if(ticketService.count(queryWrapper) != 0)
            throw new SeatOccupiedException(MessageConstant.SEAT_OCCUPIED);

        return false;
    }

    @Override
    public ScheduleSeatVO getScheduleSeats(Long scheduleId) {
        //TODO 这个数据经常被访问，最好放在Redis中
        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_id", scheduleId);
        List<Ticket> tickets = ticketService.list(queryWrapper);
        Map<Long, Boolean> seatIdMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            seatIdMap.put(ticket.getSeatId(), true);
        }


        Schedule schedule = scheduleService.getById(scheduleId);
        QueryWrapper<Seat> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("screening_hall_id", schedule.getScreeningHallId());
        //TODO 这里状态不一定是DISABLED
        queryWrapper2.ne("status", StatusConstant.DISABLED);
        List<Seat> seats = seatService.list(queryWrapper2);

        long colNum = 0;
        long rowNum = 0;
        for (Seat seat : seats) {
            colNum = Math.max(colNum,seat.getPosCol());
            rowNum = Math.max(rowNum,seat.getPosRow());
            if (seatIdMap.containsKey(seat.getId())) {
                seat.setStatus(StatusConstant.OCCUPIED);
            }
        }
        Long cinemaId = schedule.getCinemaId();
        Long movieId = schedule.getMovieId();
        Long screeningHallId = schedule.getScreeningHallId();
        ScheduleSeatVO scheduleSeatVO = ScheduleSeatVO.builder()
                .cinemaId(cinemaId)
                .cinemaName(cinemaService.getById(cinemaId).getName())
                .movieId(movieId)
                .movieTitle(movieService.getById(movieId).getTitle())
                .screeningHallId(screeningHallId)
                .screeningHallName(screeningHallService.getById(screeningHallId).getName())
                .scheduleId(scheduleId)
                .scheduleInfo(schedule.getCreatedAt().toString())
                .colCount(colNum)
                .rowCount(rowNum)
                .seats(seats)
                .build();

        return scheduleSeatVO;
    }

    private void scheduleCancelTask(Long orderId) {
        Runnable cancelTask = () -> {
            checkAndCancelOrder(orderId);
        };

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime executeTime = now.plusMinutes(TimeConstant.CANCEL_DELAY_MINUTES);

        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(cancelTask, Instant.from(executeTime));
        // 你可以选择保存 scheduledFuture 到某个地方以便于后续管理（例如取消任务）
        scheduledFuture.cancel(false);
    }
    private void checkAndCancelOrder(Long orderId) {

    }
}

