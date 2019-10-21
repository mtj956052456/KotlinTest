package com.zhenqi.baseutil.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.zhenqi.baseutil.Constant;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/3/21
 * 描述:
 */
public class BaseApp extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Utils.init(this);
        if (Constant.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    public static Context getApp() {
        return context;
    }


}
