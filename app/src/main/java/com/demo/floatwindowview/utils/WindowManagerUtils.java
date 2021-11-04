package com.demo.floatwindowview.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.demo.floatwindowview.MyApp;

import java.security.Permission;

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
            } else if (Build.VERSION.SDK_INT >= 24){
                // android7.0不能用TYPE_TOAST
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                // 以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限
                String packageName = MyApp.getApp().getPackageName();
                PackageManager pm = MyApp.getApp().getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packageName));
                if (permission) {
                    mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                } else {
                    mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                }
            }
            mLayoutParams.format = PixelFormat.RGBA_8888;
            mLayoutParams.gravity = Gravity.TOP | Gravity.START;
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (view != null && view.isAttachedToWindow()) {
                mWindowManager.removeView(view);
            }
        } else {
            if (view != null) {
                mWindowManager.removeView(view);
            }
        }

    }
}
