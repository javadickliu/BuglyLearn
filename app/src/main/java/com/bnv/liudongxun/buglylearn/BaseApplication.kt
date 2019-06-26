package com.bnv.liudongxun.buglylearn

import android.app.Application
import com.tencent.bugly.Bugly

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Bugly.init(getApplicationContext(), "df157270ca", false)
    }
}