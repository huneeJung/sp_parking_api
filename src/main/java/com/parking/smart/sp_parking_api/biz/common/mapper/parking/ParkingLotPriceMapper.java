package com.parking.smart.sp_parking_api.biz.common.mapper.parking;

import com.parking.smart.sp_parking_api.biz.parking.entity.ParkingLotPrice;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotPriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingLotPriceMapper {

    ParkingLotPriceMapper INSTANCE = Mappers.getMapper(ParkingLotPriceMapper.class);

    @Mapping(target = "parkingLot", ignore = true)
    ParkingLotPriceDto toParkingLotPriceDto(ParkingLotPrice parkingLotPrice);

}
