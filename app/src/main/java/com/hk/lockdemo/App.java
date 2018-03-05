package com.hk.lockdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/3/5.
 */

public class App extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
    }

}
