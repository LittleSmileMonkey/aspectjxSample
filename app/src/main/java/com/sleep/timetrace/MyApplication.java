package com.sleep.timetrace;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * author：xuzhenhui on 2020/8/20
 * e-mail：sleepym09@163.net
 */
public class MyApplication extends Application {

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
