package com.parking.smart.sp_parking_api.biz.common.mapper.parking;

import com.parking.smart.sp_parking_api.biz.parking.entity.ParkingLot;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingLotMapper {

    ParkingLotMapper INSTANCE = Mappers.getMapper(ParkingLotMapper.class);

    @Mapping(target = "parkingLotDetail", ignore = true)
    @Mapping(target = "parkingLotPrice", ignore = true)
    ParkingLotDto toParkingLotDto(ParkingLot parkingLot);
}
