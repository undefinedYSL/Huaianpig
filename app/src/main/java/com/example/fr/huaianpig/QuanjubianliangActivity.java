package com.example.fr.huaianpig;

import android.app.Application;

/**
 * Created by FR on 2017/7/23.
 */
public class QuanjubianliangActivity extends Application {
    private String myip ;
    public String getIp(){
        return myip;
    }
    public void setIp(String s){
        this.myip = s;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        setIp("http://121.196.198.106:9999/myApps"); //初始化全局变量
//        setIp("http://192.168.43.235:9999/myApps");
    }
}
