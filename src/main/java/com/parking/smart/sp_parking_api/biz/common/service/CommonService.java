package com.parking.smart.sp_parking_api.biz.common.service;

import com.parking.smart.sp_parking_api.biz.holiday.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final HolidayService holidayService;

    public static String getTime() {
        var now = LocalTime.now();
        var formatter = DateTimeFormatter.ofPattern("HHmm");
        return now.format(formatter);
    }

    public static String getDate() {
        var now = LocalTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(formatter);
    }

    public static boolean isWeekend() {
        var date = LocalDate.now();
        var dayOfWeek = date.getDayOfWeek();
        var dayOfWeekNumber = dayOfWeek.getValue();
        return dayOfWeekNumber == 6 || dayOfWeekNumber == 7;
    }

    public boolean isHoliday() {
        var optional = holidayService.getHoliday(getTime());
        return optional.isPresent();
    }

    public String kindOfDay() {
        if (isWeekend()) {
            return "weekend";
        } else {
            if (isHoliday()) {
                return "holiday";
            } else {
                return "weekday";
            }
        }
    }
}
