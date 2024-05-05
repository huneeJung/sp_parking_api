package com.parking.smart.sp_parking_api.biz.common.exception;

public class AlertException extends RuntimeException {

    private final Integer statusCode;
    private final String message;

    public AlertException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
