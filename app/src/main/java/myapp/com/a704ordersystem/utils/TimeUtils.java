package myapp.com.a704ordersystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时 间: 2016/11/18 0018
 */

public class TimeUtils {

    /**
     * 将时间戳格式化
     * @param data
     * @return
     */
    public static String formatData(String data){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return simpleDateFormat.format(new Date(Long.valueOf(data)*1000));

    }

}
