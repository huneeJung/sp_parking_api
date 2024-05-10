package com.parking.smart.sp_parking_api.biz.common.exception;

import com.parking.smart.sp_parking_api.biz.common.model.ParkingResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.parking.smart.sp_parking_api.biz.common.constant.Constant.HttpConstant.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ParkingResponse handleException(RuntimeException e) {
        if (e instanceof AlertException) {
            return ParkingResponse.builder()
                    .result(CLIENT_FAIL_CODE)
                    .message(FAIL_MESSAGE)
                    .result(e.getMessage())
                    .build();
        } else {
            return ParkingResponse.builder()
                    .result(SERVER_FAIL_CODE)
                    .message(FAIL_MESSAGE)
                    .result("고객센터에 문의해주세요.")
                    .build();
        }
    }

}
