package myapp.com.a704ordersystem.application;

import android.app.Application;
import android.content.SharedPreferences;

import org.xutils.x;

/**
 * 时 间: 2016/11/18 0018
 */

public class MyApplication extends Application {

    public static String token;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xUtils3的初始化
        System.out.println("初始化成功...");

        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
    }

}
