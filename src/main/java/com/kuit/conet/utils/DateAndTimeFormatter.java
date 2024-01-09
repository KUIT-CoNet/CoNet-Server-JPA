package com.kuit.conet.utils;

import com.kuit.conet.dto.web.request.plan.FixPlanRequestDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DateAndTimeFormatter {
    private static final String REGEX = "-";
    private static final int DATE_INDEX = 2;
    private static final String MINUTE_AND_SECOND = ":00:00";
    private static final String CONET_DATE_FORMAT = "yyyy. MM. dd";
    private static final String CONET_TIME_FORMAT = "HH:mm";

    public static List<Integer> datesToIntegerList(List<Date> dates) {
        return dates.stream()
                .map(Date::toString)
                .map(date -> Integer.parseInt(date.split(REGEX)[DATE_INDEX]))
                .toList();
    }

    public static String dateToStringWithDot(Date date) {
        // yyyy-MM-dd -> yyyy. MM. dd
        SimpleDateFormat dateFormat = new SimpleDateFormat(CONET_DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String timeDeleteSeconds(Time time) {
        // HH:mm:ss -> HH:mm
        SimpleDateFormat dateFormat = new SimpleDateFormat(CONET_TIME_FORMAT);
        return dateFormat.format(time);
    }

    public static Time timeIntegerToTime(FixPlanRequestDTO planRequest) {
        Long longTime = planRequest.getFixedTime();
        String strTime = longTime.toString() + MINUTE_AND_SECOND;
        return Time.valueOf(strTime);
    }

}
