package com.demo.floatwindowview.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.demo.floatwindowview.MyApp;

/**
 * Author: weichangzhou3
 * Date: 2021/10/29 18:35
 * Description: window manager处理类
 */
public class WindowManagerUtils {

    public static WindowManager mWindowManager;
    public static WindowManager.LayoutParams mLayoutParams;

    public static WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) MyApp.getApp().getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 获取float window的layout params
     * @return
     */
    public static WindowManager.LayoutParams getFloatWindowParams() {
        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            mLayoutParams.format = PixelFormat.RGBA_8888;
            mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.flags = 40;
        }
        return mLayoutParams;
    }

    /**
     * 给float window上添加自定义view
     */
    public static void addViewToFloatWindow(View view, WindowManager.LayoutParams layoutParams) {
        if (view == null || layoutParams == null) return;
        if (mWindowManager == null) {
            mWindowManager = getWindowManager();
        }
        mWindowManager.addView(view, layoutParams);
    }

    /**
     * 更新float window的位置
     */
    public static void updateFloatWindowPosition(View view, WindowManager.LayoutParams layoutParams) {
        mWindowManager.updateViewLayout(view, layoutParams);
    }

    /**
     * 移除float window
     */
    public static void removeFloatWindow(View view) {
        if (view != null) {
            mWindowManager.removeView(view);
        }
    }
}
