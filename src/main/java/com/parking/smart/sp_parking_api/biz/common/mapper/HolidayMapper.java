package com.parking.smart.sp_parking_api.biz.common.mapper;

import com.parking.smart.sp_parking_api.biz.holiday.entity.Holiday;
import com.parking.smart.sp_parking_api.biz.holiday.model.HolidayDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HolidayMapper {

    HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    HolidayDto toHolidayDto(Holiday holiday);
}
