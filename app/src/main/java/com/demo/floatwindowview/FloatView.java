package com.demo.floatwindowview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demo.floatwindowview.utils.DpiUtils;
import com.demo.floatwindowview.utils.WindowManagerUtils;

/**
 * Author: weichangzhou3
 * Date: 2021/10/29 18:12
 * Description: 可自由移动的floatView
 */
public class FloatView extends RelativeLayout {

    public Point viewPosition = new Point(0, 0);
    private boolean isMoved = false;
    private float xInView;
    private float yInView;
    private float xInLayout;
    private float yInLayout;
    private Context mContext;

    public FloatView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.float_view_layout, this);
        ImageButton btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManagerUtils.removeFloatWindow(FloatView.this);
            }
        });
        initPosition();
        MyApp myApp = (MyApp) MyApp.getApp();
        myApp.setBackgroundListener(new OnBackgroundListener() {
            @Override
            public void goBackground() {
                // 用户退出后台之后，移除float window
                WindowManagerUtils.removeFloatWindow(FloatView.this);
            }
        });
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化float view的位置
     */
    private void initPosition() {
        viewPosition.x = getScreenWidth() - DpiUtils.dp2px(100) - 40;
        viewPosition.y = getScreenHeight() - DpiUtils.dp2px(150) - 400;
        Log.e("initPosition", viewPosition.x + " " + viewPosition.y);
    }

    private int getScreenWidth() {
        Point screenSize = new Point();
        WindowManagerUtils.getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        return screenSize.x;
    }

    private int getScreenHeight() {
        Point screenSize = new Point();
        WindowManagerUtils.getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        return screenSize.y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewGroup.LayoutParams vglp;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                // 记录手指相对于屏幕按下的位置
                xInView = event.getRawX();
                yInView = event.getRawY();
                Log.e("action down", xInView + " " + yInView);
                vglp = getLayoutParams();
                if (vglp instanceof WindowManager.LayoutParams) {
                    // 记录float window实时的位置，当用户手指离开，且下次重新开始滑动时，这里将作为起始位置
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) vglp;
                    xInLayout = params.x;
                    yInLayout = params.y;
                }
                isMoved = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.e("action move", event.getRawX() + " " + event.getRawY());
                int offX = (int)(event.getRawX() - xInView);
                int offY = (int)(event.getRawY() - yInView);
                Log.e("action move", offX + " " + offY);
                // 移动超过5个像素算手指移动了
                if (Math.abs(offX) > 5 || Math.abs(offY) > 5) {
                    isMoved = true;
                }
                if (isMoved) {
                    vglp = getLayoutParams();
                    if (vglp instanceof WindowManager.LayoutParams) {
                        WindowManager.LayoutParams params = (WindowManager.LayoutParams) vglp;
                        // 根据偏移量来更新float window的位置
                        params.x = (int) (xInLayout + offX);
                        params.y = (int) (yInLayout + offY);
                        WindowManagerUtils.updateFloatWindowPosition(this, params);
                        Log.e("action result", (params.x) + " " + (params.y));
                    }

                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (!isMoved) {
                    floatViewClick();
                }
                break;
            }
        }
        return true;
    }

    private void floatViewClick() {
        if (mContext == null) return;
        Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
    }

}
