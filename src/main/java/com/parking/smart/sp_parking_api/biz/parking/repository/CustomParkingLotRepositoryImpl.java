package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.common.service.CommonService;
import com.parking.smart.sp_parking_api.biz.parking.model.response.ParkingLotResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
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

    public Page<ParkingLotResponse> getAllParkingLots(int page, int size, BooleanExpression condition, String kindOfDay) {

        BooleanPath freeField;
        StringPath openField;
        StringPath closeField;

        var now = Integer.parseInt(CommonService.getTime());

        switch (kindOfDay) {
            case "weekend": {
                freeField = parkingLot.weekendFree;
                openField = parkingLot.weekendOpen;
                closeField = parkingLot.weekendClose;
                break;
            }
            case "holiday": {
                freeField = parkingLot.holidayFree;
                openField = parkingLot.holidayOpen;
                closeField = parkingLot.holidayClose;
                break;
            }
            default: {
                freeField = parkingLot.isFree;
                openField = parkingLot.weekdayOpen;
                closeField = parkingLot.weekdayClose;
            }
        }

        List<ParkingLotResponse> result = factory.select(
                        Projections.constructor(
                                ParkingLotResponse.class,
                                parkingLot.id,
                                parkingLot.name,
                                parkingLot.address,
                                freeField,
                                openField,
                                closeField,
                                new CaseBuilder()
                                        .when(openField.castToNum(Integer.class).loe(now)
                                                .and(closeField.castToNum(Integer.class).goe(now))
                                        ).then(true)
                                        .otherwise(false).as("isOperating")
                        )
                ).from(parkingLot)
                .where(condition)
                .offset(page)
                .limit(size)
                .fetch();
        return new PageImpl<>(result, Pageable.ofSize(size), result.size());
    }

}
