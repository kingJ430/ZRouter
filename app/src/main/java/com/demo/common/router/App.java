package com.demo.common.router;

import android.app.Application;

/**
 * user: zhangjianfeng
 * date: 19/07/2017
 * version: 7.3
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SchemeManager.init();
    }
}
