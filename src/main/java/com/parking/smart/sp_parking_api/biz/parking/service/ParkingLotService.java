package com.parking.smart.sp_parking_api.biz.parking.service;

import com.parking.smart.sp_parking_api.biz.parking.model.ParkingLotDetailResponse;
import com.parking.smart.sp_parking_api.biz.parking.model.ParkingLotResponse;
import com.parking.smart.sp_parking_api.biz.parking.repository.CustomParkingLotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.parking.smart.sp_parking_api.biz.common.service.CommonUtil.getTime;
import static com.parking.smart.sp_parking_api.biz.common.service.CommonUtil.isWeekend;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final CustomParkingLotRepository customParkingLotRepository;

    public Page<ParkingLotResponse> getParkingLotList(Pageable pageable) {
        return customParkingLotRepository.getAllParkingLots(
                pageable, getTime(), isWeekend() ? "weekend" : "weekday"
        );
    }

    public ParkingLotDetailResponse getParkingLotDetails(Long id) {
        return null;
    }

}
