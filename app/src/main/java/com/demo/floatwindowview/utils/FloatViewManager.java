package com.demo.floatwindowview.utils;

import android.content.Context;
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

    // 思考如何不依赖于当前context
    public void showFloatView(Context context) {
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
