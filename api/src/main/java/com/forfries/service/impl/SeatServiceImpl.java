package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.constant.StatusConstant;
import com.forfries.dto.SeatCoordinate;
import com.forfries.dto.SeatDTO;
import com.forfries.entity.ScreeningHall;
import com.forfries.entity.Seat;
import com.forfries.service.ScreeningHallService;
import com.forfries.service.SeatService;
import com.forfries.mapper.SeatMapper;
import com.forfries.vo.SeatVO;
import com.jayway.jsonpath.internal.function.numeric.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author Nolan
* @description 针对表【seat】的数据库操作Service实现
* @createDate 2024-10-30 15:37:52
*/
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat>
    implements SeatService{


    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private ScreeningHallService screeningHallService;

    @Override
    public boolean addSeats(SeatDTO seatDTO) {

        long screeningHallId = seatDTO.getScreeningHallId();

        // 对座位坐标按行和列进行排序
        List<SeatCoordinate> sortedCoordinates = new ArrayList<>(seatDTO.getSeatCoordinates());
        sortedCoordinates.sort(Comparator.comparingInt(SeatCoordinate::getRow).thenComparingInt(SeatCoordinate::getCol));

        // 创建座位
        List<Seat> seats = new ArrayList<>();
        int colNumber = 1; // 座位号从1开始
        int rowNumber = 1;
        int row = 1; // 座位号从1开始
        int col = 1;
        for (SeatCoordinate coordinate : sortedCoordinates) {
            if(coordinate.getRow()!=row)
            {
                colNumber=1;
                rowNumber++;
            }
            row = coordinate.getRow();
            col = coordinate.getCol();
            Seat seat = Seat.builder()
                    .screeningHallId(screeningHallId)
                    .status(StatusConstant.NORMAL) // 默认座位可用
                    .posRow(row)
                    .posCol(col)
                    .rowNum(String.valueOf(rowNumber)) // 行号
                    .colNum(String.valueOf(colNumber)) // 列号
                    .build();

            seats.add(seat);
            colNumber++; // 座位号递增
        }

        this.saveBatch(seats);

        ScreeningHall updateScreeningHall = new ScreeningHall();
        updateScreeningHall.setId(screeningHallId);
        updateScreeningHall.setStatus(StatusConstant.NORMAL);


        screeningHallService.updateById(updateScreeningHall);

        return true;
    }

    public boolean deleteSeats(Long screeningHallId){

        // 创建 QueryWrapper 并设置删除条件
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("screening_hall_id", screeningHallId);

        // 执行删除操作
        seatMapper.delete(queryWrapper);

        return true;
    }

    @Override
    public SeatVO getSeats(Long screeningHallId) {
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("screening_hall_id", screeningHallId);
        //TODO 这里状态
        queryWrapper.ne("status", StatusConstant.DISABLED);
        List<Seat> seats = seatMapper.selectList(queryWrapper);
        long colNum = 0;
        long rowNum = 0;
        for (Seat seat : seats) {
            colNum = Math.max(colNum,seat.getPosCol());
            rowNum = Math.max(rowNum,seat.getPosRow());
        }
        SeatVO seatVO = SeatVO.builder()
                .screeningHallId(screeningHallId)
                .colNum(colNum)
                .rowNum(rowNum)
                .seats(seats)
                .build();

        return seatVO;
    }

    @Override
    public boolean update(Long id, String status) {

        Seat seat = Seat.builder()
                .id(id)
                .status(status)
                .build();
        updateById(seat);

        return true;
    }

}




