package com.demo.floatwindowview.utils;

import android.util.DisplayMetrics;

/**
 * Author: weichangzhou3
 * Date: 2021/11/1 16:28
 * Description:
 */
public class DpiUtils {

    public static int dp2px(float dp) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManagerUtils.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        float density = dm.density;
        return (int) (dp * density + 0.5F);
    }
}
