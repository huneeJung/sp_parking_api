package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.parking.model.ParkingLotResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomParkingLotRepository {

    Page<ParkingLotResponse> getAllParkingLots(Pageable pageable, String time, String howDay);

}
