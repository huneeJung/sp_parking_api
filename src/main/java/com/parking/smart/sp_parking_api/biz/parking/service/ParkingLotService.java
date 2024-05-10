package com.parking.smart.sp_parking_api.biz.parking.service;

import com.parking.smart.sp_parking_api.biz.common.exception.AlertException;
import com.parking.smart.sp_parking_api.biz.common.mapper.parking.ParkingLotDetailMapper;
import com.parking.smart.sp_parking_api.biz.common.mapper.parking.ParkingLotMapper;
import com.parking.smart.sp_parking_api.biz.common.mapper.parking.ParkingLotPriceMapper;
import com.parking.smart.sp_parking_api.biz.common.service.CommonService;
import com.parking.smart.sp_parking_api.biz.parking.entity.QParkingLot;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotDto;
import com.parking.smart.sp_parking_api.biz.parking.model.request.SearchParkingLotFilter;
import com.parking.smart.sp_parking_api.biz.parking.model.response.ParkingLotResponse;
import com.parking.smart.sp_parking_api.biz.parking.repository.CustomParkingLotRepository;
import com.parking.smart.sp_parking_api.biz.parking.repository.ParkingLotRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final CommonService commonService;
    
    private final ParkingLotMapper parkingLotMapper;
    private final ParkingLotDetailMapper parkingLotDetailMapper;
    private final ParkingLotPriceMapper parkingLotPriceMapper;

    private final ParkingLotRepository parkingLotRepository;
    private final CustomParkingLotRepository customParkingLotRepository;

    public Page<ParkingLotResponse> getParkingLotList(SearchParkingLotFilter filter) {
        var condition = getCondition(filter);
        var kindOfDay = commonService.kindOfDay();
        return customParkingLotRepository.getAllParkingLots(filter.getPage(), filter.getSize(), condition, kindOfDay);
    }

    public ParkingLotDto getParkingLotDetails(Long id) {
        var optional = parkingLotRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AlertException("유효한 주차장 키가 아닙니다.");
        }
        var parkingLot = optional.get();
        var parkingLotDto = parkingLotMapper.toParkingLotDto(parkingLot);
        var parkingLotDetailDto = parkingLotDetailMapper.toParkingLotDetailDto(parkingLot.getParkingLotDetail());
        var parkingLotPriceDto = parkingLotPriceMapper.toParkingLotPriceDto(parkingLot.getParkingLotPrice());
        parkingLotDto.setParkingLotDetail(parkingLotDetailDto);
        parkingLotDto.setParkingLotPrice(parkingLotPriceDto);
        return parkingLotDto;
    }

    private BooleanExpression getCondition(SearchParkingLotFilter filter) {

        var name = filter.getName();
        var address = filter.getAddress();
        var isOperating = filter.getIsOperating();
        var isFree = filter.getIsFree();
        var lat = filter.getLat();
        var lng = filter.getLng();

        QParkingLot qParkingLot = QParkingLot.parkingLot;

        BooleanExpression condition = null;

        // 주차장 이름 필터링
        if (StringUtils.isNotBlank(name)) {
            condition = condition.and(qParkingLot.name.contains(name));
        }
        // 주소 정보 필터링
        if (StringUtils.isNotBlank(address)) {
            condition = condition.and(qParkingLot.address.contains(address));
        }
        // GPS 정보가 있는 경우, 약 11킬로 내외에 있는 주차장 필터링
        // TODO : 위도 경도 정보에 가까운 데이터 우선 필터링
        if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
            var latitudeStart = new BigDecimal(lat).subtract(new BigDecimal("0.1"));
            var latitudeEnd = new BigDecimal(lat).add(new BigDecimal("0.1"));
            var longitudeStart = new BigDecimal(lng).subtract(new BigDecimal("0.1"));
            var longitudeEnd = new BigDecimal(lng).add(new BigDecimal("0.1"));
            condition = condition.and(qParkingLot.latitude.goe(latitudeStart));
            condition = condition.and(qParkingLot.latitude.loe(latitudeEnd));
            condition = condition.and(qParkingLot.latitude.goe(longitudeStart));
            condition = condition.and(qParkingLot.latitude.loe(longitudeEnd));
        }
        // 운영여부 목록 필터링
        if (StringUtils.isBlank(isOperating) && isOperating.equals("Y")) {
            var now = CommonService.getTime();
            if (CommonService.isWeekend()) {
                condition = condition.and(qParkingLot.weekendOpen.goe(now));
                condition = condition.and(qParkingLot.weekendClose.loe(now));
                if (StringUtils.isNotBlank(isFree)) {
                    condition = condition.and(qParkingLot.weekendFree.eq(true));
                }
            } else {
                if (commonService.isHoliday()) {
                    condition = condition.and(qParkingLot.holidayOpen.goe(now));
                    condition = condition.and(qParkingLot.holidayClose.loe(now));
                    if (StringUtils.isNotBlank(isFree)) {
                        condition = condition.and(qParkingLot.holidayFree.eq(true));
                    }
                } else {
                    condition = condition.and(qParkingLot.weekdayOpen.goe(now));
                    condition = condition.and(qParkingLot.weekdayClose.loe(now));
                    if (StringUtils.isNotBlank(isFree)) {
                        condition = condition.and(qParkingLot.isFree.eq(true));
                    }
                }
            }
        }
        return condition;
    }

}
