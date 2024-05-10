package com.parking.smart.sp_parking_api.biz.holiday.service;

import com.parking.smart.sp_parking_api.biz.common.mapper.HolidayMapper;
import com.parking.smart.sp_parking_api.biz.holiday.model.HolidayDto;
import com.parking.smart.sp_parking_api.biz.holiday.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HolidayService {

    private final HolidayMapper holidayMapper;
    private final HolidayRepository holidayRepository;

    public Optional<HolidayDto> getHoliday(String date) {
        var optional = holidayRepository.findByDate(date);
        HolidayDto holidayDto = null;
        if (optional.isPresent()) {
            holidayDto = holidayMapper.toHolidayDto(optional.get());
        }
        return Optional.ofNullable(holidayDto);
    }

}
