package com.parking.smart.sp_parking_api.biz.parking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingLotStatusDto {

    @JsonProperty("PARKING_CODE")
    private String code;
    @JsonProperty("CAPACITY")
    private String capacity;
    @JsonProperty("CUR_PARKING")
    private String currentParking;

}
