package com.parking.smart.sp_parking_api.biz.parking.controller;

import com.parking.smart.sp_parking_api.biz.common.model.CommonResponse;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotCustomDto;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotDto;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotStatusDto;
import com.parking.smart.sp_parking_api.biz.parking.model.request.SearchParkingLotFilter;
import com.parking.smart.sp_parking_api.biz.parking.service.ParkingLotService;
import com.parking.smart.sp_parking_api.biz.parking.service.ParkingLotStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parking")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    private final ParkingLotStatusService parkingLotStatusService;

    @Operation(summary = "주차장 목록 조회", responses = {
            @ApiResponse(description = "주차장 목록 조회 결과", responseCode = "200")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<?> getParkingLotList(SearchParkingLotFilter request) {
        Page<ParkingLotCustomDto> result = parkingLotService.getParkingLotList(request);
        return new CommonResponse<>().success(result);
    }

    @Operation(summary = "주차장 목록 상세 조회", responses = {
            @ApiResponse(description = "주차장 목록 상세 조회 결과", responseCode = "200")
    })
    @GetMapping("/{id}")
    public CommonResponse<?> searchParkingLotDetail(@PathVariable(name = "id") Long id) {
        ParkingLotDto result = parkingLotService.getParkingLotDetail(id);
        return new CommonResponse<>().success(result);
    }

    @Operation(summary = "주차장 목록 상세 조회", responses = {
            @ApiResponse(description = "주차장 목록 상세 조회 결과", responseCode = "200")
    })
    @GetMapping("/status/{id}")
    public CommonResponse<?> getParkingLotStatus(@PathVariable(name = "id") Long id, @RequestParam(name = "address") @NotEmpty String address) {
        ParkingLotStatusDto result = parkingLotStatusService.getParkingLotStatus(id, address);
        return new CommonResponse<>().success(result);
    }

}
