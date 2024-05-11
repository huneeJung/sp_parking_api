package com.parking.smart.sp_parking_api.biz.parking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.smart.sp_parking_api.biz.common.exception.AlertException;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotStatusDto;
import com.parking.smart.sp_parking_api.biz.parking.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ParkingLotStatusService {

    private final ParkingLotRepository parkingLotRepository;

    @Value("${parking.openapi.status.url}")
    private String url;
    @Value("${parking.openapi.status.key}")
    private String authKey;
    @Value("${parking.openapi.status.type}")
    private String dataType;
    @Value("${parking.openapi.status.service}")
    private String service;

    // TODO : 실시간 요청마다 매번 외부 API 요청은 리소스가 낭비됨, 개선 사항 고민
    public ParkingLotStatusDto getParkingLotStatus(Long id, String address) {
        // 실시간 정보 제공중인 데이터인지 확인
        var optional = parkingLotRepository.findById(id);
        if (optional.isEmpty()) throw new AlertException("잘못된 공영 주차장 ID 요청입니다.");
        var selectedParkingLot = optional.get();
        if (!selectedParkingLot.getAddress().equals(address)) throw new AlertException("잘못된 공영 주차장 주소 요청입니다.");
        var code = selectedParkingLot.getCode();
        var selectedParkingDetail = selectedParkingLot.getParkingLotDetail();
        var capacity = selectedParkingDetail.getCapacity() == 1 ? "정보 미제공중"
                : String.valueOf(selectedParkingDetail.getCapacity());
        if (selectedParkingDetail.getRealTimeInfo().equals(0)) {
            var currentParking = "정보 미제공중";
            return new ParkingLotStatusDto(code, capacity, currentParking);
        }

        // 주소에 매칭되는 주차장 데이터 1개 요청 ( 중복 데이터가 존재 )
        var requestUrl = url + File.separator + authKey + File.separator + dataType
                + File.separator + service + File.separator + 1 + File.separator + 1
                + File.separator + address.trim();

        // 실시간 서울시 시영주차장 OpenAPI 요청
        var restTemplate = new RestTemplate();
        var response = restTemplate.exchange(
                requestUrl, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Map<String, Object>>() {
                });
        var body = response.getBody();
        if (response.getStatusCode().value() != 200 || body == null || body.get(service) == null) {
            log.info("ParkingLot Status OpenAPI Request Failed ::: requestUrl {}", requestUrl);
            throw new AlertException("정보 요청중 오류가 발생하였습니다.\n잠시후 다시 요청해주세요.");
        }
        var parkInfo = (Map<String, Object>) body.get(service);
        var totalCount = (int) parkInfo.get("list_total_count");
        if (totalCount != 1) {
            var currentParking = "정보 미제공중";
            return new ParkingLotStatusDto(code, capacity, currentParking);
        }
        var om = new ObjectMapper();
        var parkingLotStatusDtoList = om.convertValue(parkInfo.get("row"), new TypeReference<List<ParkingLotStatusDto>>() {
        });

        return parkingLotStatusDtoList.get(0);
    }
}
