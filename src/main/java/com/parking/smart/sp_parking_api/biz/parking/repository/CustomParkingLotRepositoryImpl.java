package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.parking.model.ParkingLotResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.parking.smart.sp_parking_api.biz.parking.entity.QParkingLot.parkingLot;

@Repository
@RequiredArgsConstructor
public class CustomParkingLotRepositoryImpl implements CustomParkingLotRepository {

    private final JPAQueryFactory factory;

    public Page<ParkingLotResponse> getAllParkingLots(Pageable pageable, String time, String howDay) {

        StringPath conditionOpenField;
        StringPath conditionCloseField;

        var now = Integer.parseInt(time);

        switch (howDay) {
            case "weekend": {
                conditionOpenField = parkingLot.weekendOpen;
                conditionCloseField = parkingLot.weekendClose;
                break;
            }
            case "holiday": {
                conditionOpenField = parkingLot.holidayOpen;
                conditionCloseField = parkingLot.holidayClose;
                break;
            }
            default: {
                conditionOpenField = parkingLot.weekdayOpen;
                conditionCloseField = parkingLot.weekdayClose;
            }
        }

        List<ParkingLotResponse> result = factory.select(
                        Projections.constructor(
                                ParkingLotResponse.class,
                                parkingLot.id,
                                parkingLot.name,
                                parkingLot.address,
                                new CaseBuilder()
                                        .when(conditionOpenField.castToNum(Integer.class).loe(now)
                                                .and(conditionCloseField.castToNum(Integer.class).goe(now))
                                        ).then(true)
                                        .otherwise(false).as("isOperating")
                        )
                ).from(parkingLot)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result, pageable, result.size());
    }

}
