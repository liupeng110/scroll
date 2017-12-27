package com.andlp.scroll;

import android.app.Application;

import com.andlp.scroll.util.CrashUtil;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**BaseFragmentAdapter
 * 717219917@qq.com      2017/12/26  10:42
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtil.getInstance().init(this);
        Stetho.initializeWithDefaults(this);//调试工具
        Logger.addLogAdapter(new AndroidLogAdapter());
    }



}
