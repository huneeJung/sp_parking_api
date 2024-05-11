package com.parking.smart.sp_parking_api.biz.parking.entity;

import com.parking.smart.sp_parking_api.biz.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARKING_LOT_DETAIL")
public class ParkingLotDetail extends CommonEntity {

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "TYPE_CODE")
    private String typeCode;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "OPERATION_CODE")
    private Integer operationCode;

    @Column(name = "OPERATION_NAME")
    private String operationName;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "LAST_SYNC")
    private LocalDateTime lastSync;

    @Column(name = "REAL_TIME_INFO")
    private Integer realTimeInfo;

    @Column(name = "REAL_TIME_INFO_DESCRIPTION")
    private String realTimeInfoDescription;

    @Column(name = "NIGHT_OPEN")
    private String nightOpen;

    @Column(name = "IS_NIGHT_FREE")
    private Boolean isNightFree;

    @OneToOne
    @JoinColumn(name = "ID")
    private ParkingLot parkingLot;

}
