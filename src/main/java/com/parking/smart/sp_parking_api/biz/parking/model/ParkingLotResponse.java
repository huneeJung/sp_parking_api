package com.parking.smart.sp_parking_api.biz.parking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ParkingLotResponse {

    private String id;
    private String name;
    private String address;

    private boolean isOperating;

}
