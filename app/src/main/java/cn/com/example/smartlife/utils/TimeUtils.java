package cn.com.example.smartlife.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rd0404 on 2017/12/11.
 */

public class TimeUtils {

    //16进制转10进制
    //Integer.parseInt("8C",16);
    public static String setDate(int time) {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 0, 01, 0, 0, 0);
        return getTime(new Date((cal.getTime().getTime() + 1000 * time)));
    }

    private static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("ss");
        return format.format(date);
    }

    public static String getTimeHx(String time) {
      //  String[] arr = time.split(":");
     //   int times = Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
        int times = Integer.parseInt(time);
        //10进制转16进制
        return Integer.toHexString(times);
    }
}
