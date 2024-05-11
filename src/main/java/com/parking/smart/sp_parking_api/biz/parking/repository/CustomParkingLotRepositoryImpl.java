package com.parking.smart.sp_parking_api.biz.parking.repository;

import com.parking.smart.sp_parking_api.biz.common.service.CommonService;
import com.parking.smart.sp_parking_api.biz.parking.model.response.ParkingLotResponse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.parking.smart.sp_parking_api.biz.parking.entity.QParkingLot.parkingLot;

@RequiredArgsConstructor
public class CustomParkingLotRepositoryImpl implements CustomParkingLotRepository {

    private final JPAQueryFactory factory;

    public Page<ParkingLotResponse> getAllParkingLots(BooleanExpression condition, int page, int size, String lat, String lng, String kindOfDay) {

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

        BooleanExpression isOperating = new CaseBuilder()
                .when(openField.castToNum(Integer.class).loe(now)
                        .and(closeField.castToNum(Integer.class).goe(now))
                ).then(true)
                .otherwise(false);

        NumberExpression<?> distance = null;
        if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
            distance = Expressions.numberTemplate(Double.class, "ST_Distance_Sphere({0}, {1})",
                    Expressions.stringTemplate("POINT({0}, {1})",
                            parkingLot.longitude,
                            parkingLot.latitude
                    ),
                    Expressions.stringTemplate("POINT({0}, {1})",
                            new BigDecimal(lng),
                            new BigDecimal(lat)
                    )
            );
        }

        List<Expression<?>> selectColumns = new ArrayList<>();
        selectColumns.add(parkingLot.id);
        selectColumns.add(parkingLot.name);
        selectColumns.add(parkingLot.address);
        selectColumns.add(freeField);
        selectColumns.add(openField);
        selectColumns.add(closeField);
        // 위경도 값이 있을 경우에만 distance 컬럼 추가
        if (distance != null) {
            selectColumns.add(distance.as("distance"));
        }
        selectColumns.add(isOperating.as("isOperating"));

        // 쿼리 시작
        JPAQuery<ParkingLotResponse> query = factory.select(
                        Projections.constructor(
                                ParkingLotResponse.class,
                                selectColumns.toArray(new Expression[0])
                        )
                ).from(parkingLot)
                .where(condition);

        // distance 값이 있는 경우에만 orderBy에 추가
        if (distance != null) {
            query.orderBy(isOperating.desc(), distance.asc());
        } else {
            query.orderBy(isOperating.desc());
        }

        // 페이지네이션 적용
        query.offset(page).limit(size);
        var result = query.fetch();
        return new PageImpl<>(result, Pageable.ofSize(size), result.size());
    }

}
