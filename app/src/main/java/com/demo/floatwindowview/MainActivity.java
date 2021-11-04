package com.demo.floatwindowview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.demo.floatwindowview.utils.FloatViewManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        FloatViewManager.getInstance().showFloatView(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}