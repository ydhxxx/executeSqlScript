package com.yudh.execute.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yudh
 * @date 2022-03-21 13:52
 */
public class DateUtils {

    public static String getDateTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
