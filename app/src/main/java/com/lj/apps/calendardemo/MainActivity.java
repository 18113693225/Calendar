package com.lj.apps.calendardemo;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;


public class MainActivity extends BaseActivity implements SensorEventListener {

    @Bind(R.id.showTextView)
    TextView showTextView;
    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;
    // 将纳秒转化为秒
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float angle[] = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magneticSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        sensorManager.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magneticSensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float a = (float) Math.sqrt(x * x + y * y + z * z);
            showTextView.setText("x" + x + "y" + y + "z" + z);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            showTextView.setText("x" + x + "y" + y + "z" + z);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                angle[0] += event.values[0] * dT;
                angle[1] += event.values[1] * dT;
                angle[2] += event.values[2] * dT;
                float angle_x = (float) Math.toDegrees(angle[0]);
                float angle_y = (float) Math.toDegrees(angle[1]);
                float angle_z = (float) Math.toDegrees(angle[2]);
                showTextView.setText("x" + angle_x + "y" + angle_y + "z" + angle_z);
            }
            timestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
//TODO Auto-generated method stub
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
