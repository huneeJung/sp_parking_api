package com.parking.smart.sp_parking_api.biz.parking.controller;

import com.parking.smart.sp_parking_api.biz.common.model.ApiResponse;
import com.parking.smart.sp_parking_api.biz.parking.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parking")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping
    public ApiResponse searchParkingList(Pageable pageable) {
        var result = parkingLotService.getParkingLotList(pageable);
        return ApiResponse.success(result);
    }

    @GetMapping("/{id}")
    public ApiResponse searchParkingDetail(@PathVariable Long id) {
        var result = parkingLotService.getParkingLotDetails(id);
        return ApiResponse.success(result);
    }

}
