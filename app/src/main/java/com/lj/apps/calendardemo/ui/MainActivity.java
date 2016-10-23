package com.lj.apps.calendardemo.ui;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.lj.apps.calendardemo.Utils.SensorInfo;
import com.lj.apps.calendardemo.widget.Ball;


public class MainActivity extends BaseActivity implements SensorEventListener {
    GLSurfaceView mGlSurfaceView;
    Ball mBall;
    private float mPreviousY;
    private float mPreviousX;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float angle[] = new float[3];
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlSurfaceView = new GLSurfaceView(this);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mBall = new Ball(this);
        mGlSurfaceView.setRenderer(mBall);
        setContentView(mGlSurfaceView);
        initSensor();
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (null != gyroscopeSensor) {
            sensorManager.registerListener(this, gyroscopeSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                flag = false;
                float dy = y - mPreviousY;
                float dx = x - mPreviousX;
                mBall.yAngle += dx * 0.3f;
                mBall.xAngle += dy * 0.3f;
                break;
            case MotionEvent.ACTION_UP:
                flag = true;
                break;
            default:
                break;
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
        sensorManager.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mGlSurfaceView) {
            mGlSurfaceView.onPause();
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                angle[0] += event.values[0] * dT;
                angle[1] += event.values[1] * dT;
                angle[2] += event.values[2] * dT;
                float angle_x = (float) Math.toDegrees(angle[0]);
                float angle_y = (float) Math.toDegrees(angle[1]);
                float angle_z = (float) Math.toDegrees(angle[2]);
                if (flag) {
                    SensorInfo info = new SensorInfo();
                    info.setSensorX(angle_y);
                    info.setSensorY(angle_x);
                    info.setSensorZ(angle_z);
                    Message msg = new Message();
                    msg.what = 101;
                    msg.obj = info;
                    mHandler.sendMessage(msg);
                }
            }
        }
        timestamp = event.timestamp;

    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 101:
                    SensorInfo info = (SensorInfo) msg.obj;
                    float y = info.getSensorY();
                    float x = info.getSensorX();
                    float dy = y - mPreviousY;
                    float dx = x - mPreviousX;
                    mBall.yAngle += dx * 1.0f;
                    mBall.xAngle += dy * 1.0f;
                    mPreviousY = y;
                    mPreviousX = x;
                    break;
                default:
                    break;
            }
        }

    };

}
