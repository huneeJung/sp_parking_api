package com.parking.smart.sp_parking_api.biz.parking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotDetailDto {

    private String code;
    private String typeCode;
    private String typeName;
    private Integer operationCode;
    private String operationName;
    private String tel;
    private LocalDateTime lastSync;
    private Integer realTimeInfo;
    private String realTimeInfoDescription;
    private String nightOpen;
    private Boolean isNightFree;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
