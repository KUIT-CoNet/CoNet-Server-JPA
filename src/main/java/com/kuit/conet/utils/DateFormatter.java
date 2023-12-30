package com.kuit.conet.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class DateFormatter {
    private static final int DATE_INDEX = 2;

    public static List<Integer> datesToIntegerList(List<Date> dates) {
        return dates.stream()
                .map(Date::toString)
                .map(date -> Integer.parseInt(date.split("-")[DATE_INDEX]))
                .toList();
    }

}