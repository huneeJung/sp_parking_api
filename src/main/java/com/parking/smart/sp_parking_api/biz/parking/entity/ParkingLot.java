package com.parking.smart.sp_parking_api.biz.parking.entity;

import com.parking.smart.sp_parking_api.biz.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARKING_LOT")
public class ParkingLot extends CommonEntity {

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "WEEKDAY_OPEN")
    private String weekdayOpen;

    @Column(name = "WEEKDAY_CLOSE")
    private String weekdayClose;

    @Column(name = "WEEKEND_OPEN")
    private String weekendOpen;

    @Column(name = "WEEKEND_CLOSE")
    private String weekendClose;

    @Column(name = "HOLIDAY_OPEN")
    private String holidayOpen;

    @Column(name = "HOLIDAY_CLOSE")
    private String holidayClose;

    @Column(name = "IS_FREE")
    private Boolean isFree;

    @Column(name = "WEEKEND_FREE")
    private Boolean weekendFree;

    @Column(name = "HOLIDAY_FREE")
    private Boolean holidayFree;

    @Column(name = "LATITUDE", precision = 20, scale = 8)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 20, scale = 8)
    private BigDecimal longitude;

    @OneToOne(mappedBy = "parkingLot", fetch = LAZY, cascade = ALL)
    private ParkingLotPrice parkingLotPrice;

    @OneToOne(mappedBy = "parkingLot", fetch = LAZY, cascade = ALL)
    private ParkingLotDetail parkingLotDetail;

}


