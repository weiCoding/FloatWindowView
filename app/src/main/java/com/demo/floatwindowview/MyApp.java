package com.demo.floatwindowview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: weichangzhou3
 * Date: 2021/10/29 18:41
 * Description:
 */
public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static MyApp mApp;
    private AtomicInteger stateCounter = new AtomicInteger();
    private OnBackgroundListener mListener;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        registerActivityLifecycleCallbacks(this);
    }

    public static Application getApp() {
        return mApp;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        stateCounter.incrementAndGet();
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (stateCounter.decrementAndGet() == 0) {
            Log.e("TAG", "进入后台");
            if (mListener != null) {
                mListener.goBackground();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    public void setBackgroundListener(OnBackgroundListener listener) {
        mListener = listener;
    }

    /**
     * 判断用户是否进入前台
     * @return
     */
    public boolean isAppForeground() {
        return stateCounter.get() > 0;
    }
}
