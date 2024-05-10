package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.parking.model.response.ParkingLotResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomParkingLotRepository {

    Page<ParkingLotResponse> getAllParkingLots(int page, int size, BooleanExpression condition, String kindOfDay);

}
