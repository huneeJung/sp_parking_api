package com.parking.smart.sp_parking_api.biz.parking.controller;

import com.parking.smart.sp_parking_api.biz.common.model.ParkingResponse;
import com.parking.smart.sp_parking_api.biz.parking.model.request.SearchParkingLotFilter;
import com.parking.smart.sp_parking_api.biz.parking.service.ParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    @GetMapping
    public ParkingResponse searchParkingList(SearchParkingLotFilter request) {
        var result = parkingLotService.getParkingLotList(request);
        return ParkingResponse.success(result);
    }

    @Operation(summary = "주차장 목록 상세 조회", responses = {
            @ApiResponse(description = "주차장 목록 상세 조회 결과", responseCode = "200")
    })
    @GetMapping("/{id}")
    public ParkingResponse searchParkingDetail(@PathVariable Long id) {
        var result = parkingLotService.getParkingLotDetails(id);
        return ParkingResponse.success(result);
    }

}
