package com.forfries.service.common;

import com.forfries.dto.SeatDTO;
import com.forfries.entity.Seat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forfries.vo.SeatVO;

import java.util.List;

/**
* @author Nolan
* @description 针对表【seat】的数据库操作Service
* @createDate 2024-10-30 15:37:52
*/
public interface SeatService extends IService<Seat> {

    boolean addSeats(SeatDTO seatDTO);
    boolean deleteSeats(Long screeningHallId);

    SeatVO getSeats(Long screeningHallId);

    boolean update(Long id, String status);

    boolean checkSeatStatus(List<Long> seatIds);
}
