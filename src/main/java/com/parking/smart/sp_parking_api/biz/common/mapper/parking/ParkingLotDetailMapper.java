package com.parking.smart.sp_parking_api.biz.common.mapper.parking;

import com.parking.smart.sp_parking_api.biz.parking.entity.ParkingLotDetail;
import com.parking.smart.sp_parking_api.biz.parking.model.dto.ParkingLotDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingLotDetailMapper {

    ParkingLotDetailMapper INSTANCE = Mappers.getMapper(ParkingLotDetailMapper.class);

    //    @Mapping(target = "parkingLot", ignore = true)
    ParkingLotDetailDto toParkingLotDetailDto(ParkingLotDetail parkingLotDetail);

}
