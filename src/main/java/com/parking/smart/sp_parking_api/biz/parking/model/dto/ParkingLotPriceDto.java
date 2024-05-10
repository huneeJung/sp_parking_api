package com.parking.smart.sp_parking_api.biz.parking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotPriceDto {

    private String code;
    private Integer unitMinute;
    private BigDecimal unitPrice;
    private Integer extraMinute;
    private BigDecimal extraPrice;
    private BigDecimal dailyMaxPrice;
    private BigDecimal monthlyPassPrice;
}
