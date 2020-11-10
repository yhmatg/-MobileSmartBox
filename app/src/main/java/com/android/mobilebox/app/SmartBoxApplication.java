package com.android.mobilebox.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.mobilebox.BuildConfig;
import com.android.mobilebox.R;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.utils.Utils;
import com.android.mobilebox.utils.logger.MyCrashListener;
import com.android.mobilebox.utils.logger.TxtFormatStrategy;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.xuexiang.xlog.XLog;
import com.xuexiang.xlog.crash.CrashHandler;

/**
 * @author yhm
 * @date 2017/11/27
 */
//public class SmartBoxApplication extends Application implements HasActivityInjector {
public class SmartBoxApplication extends Application {


    private static SmartBoxApplication instance;
    private RefWatcher refWatcher;
    private UserLoginResponse userResponse;
    public static synchronized SmartBoxApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        SmartBoxApplication application = (SmartBoxApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        refWatcher = LeakCanary.install(this);
        instance = this;
        initLogger();
        //崩溃日志保存到本地
        ///storage/emulated/0/Android/data/com.common.esimrfid/cache/crash_log
        XLog.init(this);
        Utils.init(this);
        CrashHandler.getInstance().setOnCrashListener(new MyCrashListener());

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }


    private void initLogger() {
        //DEBUG版本才打控制台log
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().
                    tag(getString(R.string.app_name)).build()));
        }
        //把log存到本地
        Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder().
                tag(getString(R.string.app_name)).build(getPackageName(), getString(R.string.app_name))));
    }

    public UserLoginResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserLoginResponse userResponse) {
        this.userResponse = userResponse;
    }
}
