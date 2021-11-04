package com.demo.floatwindowview.utils;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.demo.floatwindowview.FloatView;

/**
 * Author: weichangzhou3
 * Date: 2021/11/1 11:40
 * Description: floatview管理类
 */
public class FloatViewManager {

    private static FloatView mFloatView;

    private static volatile FloatViewManager instance;

    public static FloatViewManager getInstance() {

        synchronized (FloatViewManager.class) {
            if (instance == null) {
                instance = new FloatViewManager();
            }
            return instance;
        }
    }

    public void showFloatView(Context context) {
        /**
         * 判断用户是否打开了浮窗权限
         */
        boolean result = FloatPermissionManager.getInstance().applyFloatWindow(context, new UphoneCallback() {
            @Override
            public void invoke(boolean result, String msg) {
                if (result) {
                    Log.e("FloatViewManager", "用户选择了去开启");
                } else {
                    Log.e("FloatViewManager", "用户选择了暂不开启");
                }
            }
        });
        if (result) {
            if (mFloatView == null && context != null) {
                mFloatView = new FloatView(context);
            }
            // 将float view的坐标赋值给window
            WindowManager.LayoutParams params = WindowManagerUtils.getFloatWindowParams();
            params.x = mFloatView.viewPosition.x;
            params.y = mFloatView.viewPosition.y;
            // 将float view添加到window层
            WindowManagerUtils.addViewToFloatWindow(mFloatView, params);
        }

    }

}
