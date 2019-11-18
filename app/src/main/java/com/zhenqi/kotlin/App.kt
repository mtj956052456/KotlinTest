package com.zhenqi.kotlin

import android.content.Context
import androidx.multidex.MultiDex
import com.zhenqi.baseutil.base.BaseApp

/**
 *  创建者: 孟腾蛟
 *  时间: 2019/10/21
 *  描述:
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()

    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}