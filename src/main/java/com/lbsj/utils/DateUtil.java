package com.itranlin.twin.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static final Calendar CALENDAR = Calendar.getInstance();
    // Date
    public static final SimpleDateFormat D_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat D_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat D_MONTH = new SimpleDateFormat("yyyy-MM");
    // LocalDate
    public static final DateTimeFormatter L_MONTH = DateTimeFormatter.ofPattern("yyyy-MM");

    /**
     * 获取前6月时间
     * @return
     */
    public static LocalDateTime getPastHalfYear(){
        CALENDAR.setTime(new Date());
        CALENDAR.add(Calendar.MONTH, -5);
        String format = D_MONTH.format(CALENDAR.getTime());
        String[] split = format.split("-");
        return LocalDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 1, 0, 0);
    }

    /**
     * 获取前6月月份集合
     * @return
     */
    public static List<String> getPastHalfYearMonthList(){
        List<String> list = new ArrayList<>();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, -5);
        for(int i=0;i<6;i++){
            list.add(D_MONTH.format(date.getTime()));
            date.add(Calendar.MONTH, 1);
        }
        return list;
    }




    public static void main(String[] args) {
        // getPastHalfYear1();

    }
}
