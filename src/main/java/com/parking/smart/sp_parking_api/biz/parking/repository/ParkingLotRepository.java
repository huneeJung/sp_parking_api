package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.parking.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long>, CustomParkingLotRepository {

    @Query("""
            SELECT pl FROM ParkingLot pl
            JOIN FETCH pl.parkingLotDetail pld
            JOIN FETCH pl.parkingLotPrice plp
            WHERE pl.id = :id""")
    Optional<ParkingLot> findById(@Param("id") Long id);

}
