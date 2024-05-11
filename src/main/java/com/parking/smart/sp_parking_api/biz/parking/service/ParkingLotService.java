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
import com.parking.smart.sp_parking_api.biz.parking.repository.ParkingLotRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final CommonService commonService;

    private final ParkingLotMapper parkingLotMapper;
    private final ParkingLotDetailMapper parkingLotDetailMapper;
    private final ParkingLotPriceMapper parkingLotPriceMapper;

    private final ParkingLotRepository parkingLotRepository;

    public Page<ParkingLotResponse> getParkingLotList(SearchParkingLotFilter filter) {
        var condition = getCondition(filter);
        var kindOfDay = commonService.kindOfDay();
        return parkingLotRepository.getAllParkingLots(
                condition, filter.getPage(), filter.getSize(), filter.getLat(), filter.getLng(), kindOfDay
        );
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

        BooleanExpression condition = Expressions.asBoolean(true).isTrue();

        // 주차장 이름 필터링
        if (StringUtils.isNotBlank(name)) {
            condition = condition.and(qParkingLot.name.contains(name));
        }
        // 주소 정보 필터링
        if (StringUtils.isNotBlank(address)) {
            condition = condition.and(qParkingLot.address.contains(address));
        }
        // TODO : 추가 작업 생각해보기 - 가까운 순으로 정렬은 QueryDSL 에서 처리중
        if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
//            var latitudeStart = new BigDecimal(lat).subtract(new BigDecimal("0.1"));
//            var latitudeEnd = new BigDecimal(lat).add(new BigDecimal("0.1"));
//            var longitudeStart = new BigDecimal(lng).subtract(new BigDecimal("0.1"));
//            var longitudeEnd = new BigDecimal(lng).add(new BigDecimal("0.1"));
//            condition = condition.and(qParkingLot.latitude.goe(latitudeStart));
//            condition = condition.and(qParkingLot.latitude.loe(latitudeEnd));
//            condition = condition.and(qParkingLot.latitude.goe(longitudeStart));
//            condition = condition.and(qParkingLot.latitude.loe(longitudeEnd));
        }
        // 운영여부 목록 필터링 , 운영하지 않는 주차장을 볼 상황은 없다고 생각하므로 운영중인거만 체
        if (StringUtils.isNotBlank(isOperating) && isOperating.equals("Y")) {
            var now = CommonService.getTime();
            if (CommonService.isWeekend()) {
                condition = condition.and(qParkingLot.weekendOpen.loe(now));
                condition = condition.and(qParkingLot.weekendClose.goe(now));
                if (StringUtils.isNotBlank(isFree)) {
                    condition = condition.and(qParkingLot.weekendFree.eq(true));
                }
            } else {
                if (commonService.isHoliday()) {
                    condition = condition.and(qParkingLot.holidayOpen.loe(now));
                    condition = condition.and(qParkingLot.holidayClose.goe(now));
                    if (StringUtils.isNotBlank(isFree)) {
                        condition = condition.and(qParkingLot.holidayFree.eq(true));
                    }
                } else {
                    condition = condition.and(qParkingLot.weekdayOpen.loe(now));
                    condition = condition.and(qParkingLot.weekdayClose.goe(now));
                    if (StringUtils.isNotBlank(isFree)) {
                        condition = condition.and(qParkingLot.isFree.eq(true));
                    }
                }
            }
        }
        
        return condition;
    }

}
