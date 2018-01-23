package com.example.opilane.magic8ball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView vasTekst;

    private SensorManager sensorManager;
    private  float acelValue;
    private  float acelLast;
    private float raputa;

    private String[] vastus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vasTekst = findViewById(R.id.vasTekst);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);
        acelLast= SensorManager.GRAVITY_EARTH;
        acelValue=SensorManager.GRAVITY_EARTH;
        raputa= 0.00f;
        vastus = getResources().getStringArray(R.array.vastused);
    }
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelValue;
            acelValue = (float)Math.sqrt((double)(x*x+y*y+z*z));
            float delta =acelValue - acelLast;
            raputa = raputa * 0.9f + delta;

            if (raputa > 12) {
                int randomInt = new Random().nextInt(vastus.length);
                String randomVastus = vastus[randomInt];
                vasTekst.setText(randomVastus);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
