package com.yan.newcalulator;

import android.app.Application;

import com.yan.newcalulator.util.ContextUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.setContext(this);
    }
}
