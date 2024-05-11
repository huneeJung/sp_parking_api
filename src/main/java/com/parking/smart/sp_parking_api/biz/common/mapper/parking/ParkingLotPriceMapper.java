package com.parking.smart.sp_parking_api.biz.common.mapper.parking;

import com.parking.smart.sp_parking_api.biz.parking.entity.ParkingLotPrice;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotPriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingLotPriceMapper {

    ParkingLotPriceMapper INSTANCE = Mappers.getMapper(ParkingLotPriceMapper.class);

    ParkingLotPriceDto toParkingLotPriceDto(ParkingLotPrice parkingLotPrice);

}
