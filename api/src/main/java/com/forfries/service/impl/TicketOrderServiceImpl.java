package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.forfries.annotation.CinemaIdCheck;
import com.forfries.common.Impl.PageableWithCheckServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.context.BaseContext;
import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.Schedule;
import com.forfries.entity.Seat;
import com.forfries.entity.Ticket;
import com.forfries.entity.TicketOrder;
import com.forfries.exception.InconsistentIDException;
import com.forfries.exception.SeatOccupiedException;
import com.forfries.exception.StandardizationErrorException;
import com.forfries.exception.SystemException;
import com.forfries.mapper.TicketOrderMapper;
import com.forfries.service.*;
import com.forfries.vo.ScheduleSeatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
* @author Nolan
* @description 针对表【ticket_order】的数据库操作Service实现
* @createDate 2024-10-30 15:37:57
*/
@Service
public class TicketOrderServiceImpl extends PageableWithCheckServiceImpl<TicketOrderMapper, TicketOrder, TicketOrderPageDTO>
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

//    @Autowired
//    private TaskScheduler taskScheduler;

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
            queryWrapper.like("order_num", orderNum);
        }
        Long scheduleId = ticketOrderPageDTO.getScheduleId();
        if (scheduleId != null ) {
            queryWrapper.eq("schedule_id", scheduleId);
        }
        Long userId = ticketOrderPageDTO.getUserId();
        if (userId != null ) {
            queryWrapper.eq("user_id", userId);
        }

        //这里要区分管理员和用户
        if(BaseContext.getCurrentPayload().get("role").equals(RoleConstant.ROLE_USER))
            queryWrapper.eq("user_id", BaseContext.getCurrentPayload().get("userId"));
        else
            queryWrapper.eq("cinema_id", ticketOrderPageDTO.getCinemaId());
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }

    @Override
    public boolean createTicketOrder(TicketOrderGenerationDTO ticketOrderGenerationDTO) {
        //管理员添加订单，这里的userId为管理员
        //TODO 管理员当然不用考虑锁的事情，但是用户需要

        //获取创建用户的id
        long userId = Long.parseLong(BaseContext.getCurrentPayload().get("userId"));

        //检查seatIds
        List<Long> seatIds = ticketOrderGenerationDTO.getSeatIds();
        if(seatIds == null || seatIds.isEmpty()){
            throw new StandardizationErrorException(MessageConstant.STANDARDIZATION_ERROR);
        }

        //获取schedule
        Schedule schedule = scheduleService.getById(ticketOrderGenerationDTO.getScheduleId());

        //查询座位是否与ScreeningHallId一致
        this.checkSeatBelong(seatIds,schedule.getScreeningHallId());

        //检查座位是否维修、座位是否被占领
        this.checkSeatMaintenance(seatIds);
        this.checkSeatOccupied(seatIds, schedule.getId());

        //上面全部通过后开始创建订单
        int ticketCount = seatIds.size();
        //设置状态
        //TODO 这里可能要设置不同的状态哦 目前是只要创建订单 订单就为Normal 票为Available

        BigDecimal price4One = schedule.getTicketPrice();
        TicketOrder ticketOrder = TicketOrder.builder()
                .userId(userId)
                .cinemaId(schedule.getCinemaId())
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
                    .status(StatusConstant.AVAILABLE)
                    .build();
            tickets.add(ticket);
        }

        ticketService.saveBatch(tickets);

        return true;
    }

    private void checkSeatBelong(List<Long> seatIds, Long screeningHallId) {

        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        //判断这些座位中有没有ScreeningHall和Schedule不一致的
        queryWrapper.in("id", seatIds)
                .ne("screening_hall_id", screeningHallId);

        if(seatService.count(queryWrapper) != 0)
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_SEAT_ID);

    }

    private void checkSeatMaintenance(List<Long> seatIds) {
        if(seatService.checkSeatStatus(seatIds))
            throw new SeatOccupiedException(MessageConstant.SEAT_IN_MAINTENANCE);
    }


    @Override
    public void checkSeatOccupied(List<Long> seatIds, Long scheduleId) {

        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        //查找所有seatId和scheduleId一致的有效票
        queryWrapper.in("seat_id", seatIds)
                .eq("schedule_id", scheduleId)
                //判断座位是有有票
                .eq("status", StatusConstant.AVAILABLE);

        //如果存在有效票
        if(ticketService.count(queryWrapper) != 0)
            throw new SeatOccupiedException(MessageConstant.SEAT_OCCUPIED);
    }

    @Cacheable(cacheNames = "scheduleSeats",key = "#scheduleId")
    @Override
    public ScheduleSeatVO getScheduleSeats(Long scheduleId) {
        //TODO 这个数据经常被访问，最好放在Redis中
        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_id", scheduleId);
        //获取被占领的座位
        queryWrapper.eq("status", StatusConstant.AVAILABLE);
        //根据scheduleId查找有效ticket
        List<Ticket> tickets = ticketService.list(queryWrapper);
        Map<Long, Boolean> seatIdMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            seatIdMap.put(ticket.getSeatId(), true);
        }
        //获取seatId根据scheduleId，即有票的座位

        //获取schedule
        Schedule schedule = scheduleService.getById(scheduleId);

        //根据schedule的ScreeningHall获取座位表
        QueryWrapper<Seat> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("screening_hall_id", schedule.getScreeningHallId());
        //不获取无法看见的座位 即DISABLED
        queryWrapper2.ne("status", StatusConstant.DISABLED);
        //获取所有座位
        List<Seat> seats = seatService.list(queryWrapper2);

        //获取座标为表的信息，更改对应座位的status
        long colNum = 0;
        long rowNum = 0;
        for (Seat seat : seats) {
            colNum = Math.max(colNum,seat.getPosCol());
            rowNum = Math.max(rowNum,seat.getPosRow());
            if (seatIdMap.containsKey(seat.getId())) {
                seat.setStatus(StatusConstant.OCCUPIED);
            }
        }
        //返回VO
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

    @Override
    @CinemaIdCheck
    public boolean cancelTicketOrderWithCheck(long id) {
        return cancelTicketOrder(id);
    }

    @Override
    public TicketOrder getByIdWithUserCheck(long id) {
        String userId = BaseContext.getCurrentPayload().get("userId");
        QueryWrapper<TicketOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .eq("user_id", userId);
        return getOne(queryWrapper);
    }


    @Override
    public void cancelTicketOrderWithUserCheck(long id) {
        //TODO 这里要加上管理员审核
        TicketOrder ticketOrder = getByIdWithUserCheck(id);
        if(ticketOrder!=null)
            cancelTicketOrder(id);
    }


    private boolean cancelTicketOrder(long id) {
        return updateTicketOrderStatus(id,StatusConstant.CANCELED, StatusConstant.NOT_AVAILABLE);
    }

    private boolean updateTicketOrderStatus(long id,String orderStatus,String ticketStatus) {

        UpdateWrapper<TicketOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("status", orderStatus);
        update(updateWrapper);

        UpdateWrapper<Ticket> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("order_id", id)
                .set("status", ticketStatus);
        return ticketService.update(updateWrapper2);
    }

    private void scheduleCancelTask(Long orderId) {
//        Runnable cancelTask = () -> {
//            checkAndCancelOrder(orderId);
//        };
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime executeTime = now.plusMinutes(TimeConstant.CANCEL_DELAY_MINUTES);
//
//        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(cancelTask, Instant.from(executeTime));
//        // 你可以选择保存 scheduledFuture 到某个地方以便于后续管理（例如取消任务）
//        scheduledFuture.cancel(false);
    }
    private void checkAndCancelOrder(Long orderId) {

    }
}

