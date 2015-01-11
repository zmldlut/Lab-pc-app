package com.zml.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
    public static Date getOderTime(int i) {
        Date datetime = new Date();
        Calendar calendar = new GregorianCalendar(); 
        calendar.setTime(datetime); 
        calendar.add(calendar.DATE, i / 3 + 1);//把日期往后增加一天.整数往后推,负数往前移动 
        datetime=calendar.getTime();   //这个时间就是日期往后推一天的结果
        switch(i) {
        case 1:
        case 4:
            datetime.setHours(9);
            datetime.setMinutes(0);
            datetime.setSeconds(0);
            break;
        case 2:
        case 5:
            datetime.setHours(14);
            datetime.setMinutes(0);
            datetime.setSeconds(0);
            break;
        case 3:
        case 6:
            datetime.setHours(18);
            datetime.setMinutes(0);
            datetime.setSeconds(0);
            break;
        default:
            break;
        }
        return datetime;
    }
    
    public static Date addHour(Date date, int hours) {
        date.setHours(date.getHours() + hours);
        return date;
    }
}
