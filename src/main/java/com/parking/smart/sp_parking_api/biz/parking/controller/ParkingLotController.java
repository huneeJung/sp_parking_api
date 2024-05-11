package com.parking.smart.sp_parking_api.biz.parking.controller;

import com.parking.smart.sp_parking_api.biz.common.model.CommonResponse;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotDto;
import com.parking.smart.sp_parking_api.biz.parking.model.request.SearchParkingLotFilter;
import com.parking.smart.sp_parking_api.biz.parking.model.response.ParkingLotResponse;
import com.parking.smart.sp_parking_api.biz.parking.service.ParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parking")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @Operation(summary = "주차장 목록 조회", responses = {
            @ApiResponse(description = "주차장 목록 조회 결과", responseCode = "200")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<?> searchParkingList(SearchParkingLotFilter request) {
        Page<ParkingLotResponse> result = parkingLotService.getParkingLotList(request);
        return new CommonResponse<>().success(result);
    }

    @Operation(summary = "주차장 목록 상세 조회", responses = {
            @ApiResponse(description = "주차장 목록 상세 조회 결과", responseCode = "200")
    })
    @GetMapping("/{id}")
    public CommonResponse<?> searchParkingDetail(@PathVariable(name = "id") Long id) {
        ParkingLotDto result = parkingLotService.getParkingLotDetails(id);
        return new CommonResponse<>().success(result);
    }

}
