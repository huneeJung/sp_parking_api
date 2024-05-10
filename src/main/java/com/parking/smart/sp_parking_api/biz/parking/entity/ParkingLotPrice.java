package com.parking.smart.sp_parking_api.biz.parking.entity;

import com.parking.smart.sp_parking_api.biz.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARKING_LOT_PRICE")
public class ParkingLotPrice extends CommonEntity {

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "UNIT_MINUTE")
    private Integer unitMinute;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @Column(name = "EXTRA_MINUTE")
    private Integer extraMinute;

    @Column(name = "EXTRA_PRICE")
    private BigDecimal extraPrice;

    @Column(name = "DAILY_MAX_PRICE")
    private BigDecimal dailyMaxPrice;

    @Column(name = "MONTHLY_PASS_PRICE")
    private BigDecimal monthlyPassPrice;

    @OneToOne
    @JoinColumn(name = "ID")
    private ParkingLot parkingLot;

}
