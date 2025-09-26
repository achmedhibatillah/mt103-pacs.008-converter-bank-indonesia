package com.messageconverter.kfmi.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GetDateTimeService {
    public static String getDateTime(String mtDate, String mtTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate date = LocalDate.parse(mtDate, dateFormatter);

        LocalTime time;
        if (mtTime != null && !mtTime.isEmpty()) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            time = LocalTime.parse(mtTime, timeFormatter);
        } else {
            time = LocalTime.MIDNIGHT;
        }

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' T'HH:mm");
        return dateTime.format(outputFormatter);
    }
}
