package com.parking.smart.sp_parking_api.biz.holiday.entity;

import com.parking.smart.sp_parking_api.biz.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HOLIDAY")
public class Holiday extends CommonEntity {

    @Column(name = "DATE")
    private String date;

    @Column(name = "DATE_NAME")
    private String dateName;

}


