package com.duck.ayoung.goupstair.common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

    public static LocalDate getThisMonday(LocalDate date) {
       return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
