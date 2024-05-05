package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.parking.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long>, CustomParkingLotRepository {

}
