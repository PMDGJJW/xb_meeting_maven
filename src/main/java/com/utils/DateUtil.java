package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/3/23 19:45
 * @Description
 */
public class DateUtil {


    public long time (String dateStr){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            return 0;

        }
    }
}
