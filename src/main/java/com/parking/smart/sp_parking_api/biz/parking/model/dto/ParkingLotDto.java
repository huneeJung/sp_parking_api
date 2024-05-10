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
public class ParkingLotDto {

    private String code;
    private String name;
    private String address;
    private String weekdayOpen;
    private String weekdayClose;
    private String weekendOpen;
    private String weekendClose;
    private String holidayOpen;
    private String holidayClose;
    private Boolean isFree;
    private Boolean weekendFree;
    private Boolean holidayFree;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private ParkingLotPriceDto parkingLotPrice;
    private ParkingLotDetailDto parkingLotDetail;

}
