package com.parking.smart.sp_parking_api.biz.common.exception;

public class AlertException extends RuntimeException {

    private final String message;

    public AlertException(String message) {
        this.message = message;
    }
}
