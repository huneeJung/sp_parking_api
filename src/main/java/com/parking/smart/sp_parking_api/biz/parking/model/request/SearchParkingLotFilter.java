package com.parking.smart.sp_parking_api.biz.parking.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParkingLotFilter {

    @Schema(description = "주차장 이름", type = "String")
    private String name;

    @Schema(description = "주차장 주소", type = "String")
    private String address;

    @Schema(description = "운영 유무", type = "String")
    private String isOperating;

    @Schema(description = "무료 여부", type = "String")
    private String isFree;

    @Schema(description = "사용자 위도", type = "String")
    private String lat;

    @Schema(description = "사용자 경도", type = "String")
    private String lng;

    @NotNull
    @Min(value = 0)
    @Schema(description = "목록 페이지", type = "String", required = true)
    private Integer page;

    @NotNull
    @Min(value = 0)
    @Schema(description = "목록 사이즈", type = "String", required = true)
    private Integer size;


}
