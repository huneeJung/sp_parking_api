package com.parking.smart.sp_parking_api.biz.common.model;

import lombok.Builder;

import static com.parking.smart.sp_parking_api.biz.common.constant.Constant.HttpConstant.SUCCESS_CODE;
import static com.parking.smart.sp_parking_api.biz.common.constant.Constant.HttpConstant.SUCCESS_MESSAGE;

@Builder
public class ParkingResponse {

    private final Integer statusCode;
    private final String message;
    private final Object result;

    public static ParkingResponse success(Object result) {
        return ParkingResponse.builder()
                .statusCode(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .result(result)
                .build();
    }

}