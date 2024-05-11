package com.parking.smart.sp_parking_api.biz.parking.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ParkingLotResponse {

    private Long id;
    private String name;
    private String address;
    private Boolean isFree;
    private String openTime;
    private String closeTime;
    private Double distance;
    private Boolean isOperating;

    public ParkingLotResponse(Long id, String name, String address, Boolean isFree, String openTime, String closeTime, Boolean isOperating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isFree = isFree;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.isOperating = isOperating;
    }

}
