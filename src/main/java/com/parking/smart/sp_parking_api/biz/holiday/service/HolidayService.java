package com.parking.smart.sp_parking_api.biz.holiday.service;

import com.parking.smart.sp_parking_api.biz.common.mapper.HolidayMapper;
import com.parking.smart.sp_parking_api.biz.holiday.model.HolidayDto;
import com.parking.smart.sp_parking_api.biz.holiday.repository.HolidayRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HolidayService {

    private final HolidayMapper holidayMapper;
    private final HolidayRepository holidayRepository;

    private Boolean find;
    private Optional<HolidayDto> result;

    @PostConstruct
    private void initialize() {
        find = false;
    }

    public Optional<HolidayDto> getHoliday(String date) {
        if (!find) {
            find = true;
            var optional = holidayRepository.findByDate(date);
            HolidayDto holidayDto = null;
            if (optional.isPresent()) {
                holidayDto = holidayMapper.toHolidayDto(optional.get());
            }
            result = Optional.ofNullable(holidayDto);
        }
        return result;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void init() {
        find = false;
    }

}
