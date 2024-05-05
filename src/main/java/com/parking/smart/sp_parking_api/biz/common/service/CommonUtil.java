package com.parking.smart.sp_parking_api.biz.common.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CommonUtil {

    public static String getTime() {
        var now = LocalTime.now();
        var formatter = DateTimeFormatter.ofPattern("HHmm");
        return now.format(formatter);
    }

    public static boolean isWeekend() {
        var date = LocalDate.now();
        var dayOfWeek = date.getDayOfWeek();
        var dayOfWeekNumber = dayOfWeek.getValue();
        return dayOfWeekNumber == 6 || dayOfWeekNumber == 7;
    }
}
