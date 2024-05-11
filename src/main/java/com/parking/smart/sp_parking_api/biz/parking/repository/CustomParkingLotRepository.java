package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.parking.model.response.ParkingLotResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;

public interface CustomParkingLotRepository {

    Page<ParkingLotResponse> getAllParkingLots(BooleanExpression condition, int page, int size, String lat, String lng, String kindOfDay);

}
