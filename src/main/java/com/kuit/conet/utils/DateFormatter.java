package com.kuit.conet.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DateFormatter {
    private static final String REGEX = "-";
    private static final int DATE_INDEX = 2;

    public static List<Integer> datesToIntegerList(List<Date> dates) {
        return dates.stream()
                .map(Date::toString)
                .map(date -> Integer.parseInt(date.split(REGEX)[DATE_INDEX]))
                .toList();
    }

    public static String dateToStringWithDot(Date date) {
        // yyyy-MM-dd -> yyyy. MM. dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd");
        return dateFormat.format(date);
    }

    public static String timeDeleteSeconds(Time time) {
        // HH:mm:ss -> HH:mm
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

}
