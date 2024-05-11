package com.parking.smart.sp_parking_api.biz.common.exception;

import com.parking.smart.sp_parking_api.biz.common.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.parking.smart.sp_parking_api.biz.common.constant.Constant.HttpConstant.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO : Exception 캐치가 안됨
    @ExceptionHandler(RuntimeException.class)
    public CommonResponse<?> handleException(RuntimeException e) {
        log.error("", e);
        if (e instanceof AlertException) {
            return CommonResponse.builder()
                    .success(false)
                    .code(CLIENT_FAIL_CODE)
                    .message(FAIL_MESSAGE)
                    .result(e.getMessage())
                    .build();
        } else {
            return CommonResponse.builder()
                    .success(false)
                    .code(SERVER_FAIL_CODE)
                    .message(FAIL_MESSAGE)
                    .result("고객센터에 문의해주세요.")
                    .build();
        }
    }

}
