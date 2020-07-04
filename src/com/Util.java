package com;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Util {

    public static String[] idz_nv = { "idz_nv1.jpg", "idz_nv2.jpg",
            "idz_nv3.jpg", "idz_nv4.jpg", "idz_nv5.jpg" };

    public static String[] idz_nan = { "idz_nan1.jpg", "idz_nan2.jpg",
            "idz_nan3.jpg", "idz_nan4.jpg", "idz_nan5.jpg" };


    public static String StringDecode(String param){
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }
}
