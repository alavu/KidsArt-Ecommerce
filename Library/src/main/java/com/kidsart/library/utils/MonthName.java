package com.kidsart.library.utils;

public class MonthName {

    private static final String[] MONTH_NAMES = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    public static String getMonthName(int month) {
        return MONTH_NAMES[month - 1];
    }
}
