package cn.com.example.smartlife.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by rd0404 on 2017/11/17.
 */

public class UtilsEncode {

    public static String getEncode(String str){
        String s = null;
        try {
            s = URLEncoder.encode(str, "UTF-8");
            s = s.replace("%","");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  s;
    }
}
