package com.lj.apps.calendardemo.ui;


import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import com.lj.apps.calendardemo.widget.CustomRenderer;


public class MainActivity extends BaseActivity {
    GLSurfaceView mGlSurfaceView;
    CustomRenderer mBall;
    private float mPreviousY;
    private float mPreviousX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlSurfaceView = new GLSurfaceView(this);
        mBall = new CustomRenderer(this);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderer(mBall);
        setContentView(mGlSurfaceView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;
                float dx = x - mPreviousX;
                mBall.yAngle += dx * 0.3f;
                mBall.xAngle += dy * 0.3f;
        }
        mPreviousY = y;
        mPreviousX = x;
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null != mGlSurfaceView) {
            mGlSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mGlSurfaceView) {
            mGlSurfaceView.onPause();
        }
    }

}
