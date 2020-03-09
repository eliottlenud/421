package io.hei.a421;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Shakeit extends AppCompatActivity {
    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;

    private static int SHAKE_THRESHOLD = 3;
    final SensorEventListener mSensorEventListener = new SensorEventListener() {

        private void generateRandomNumber() {
            Random randomGenerator = new Random();
            int randomNum = randomGenerator.nextInt(6) + 1;
            System.out.println("LE Dé A FAIT =" + randomNum);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            System.out.println("x=" + x);
            System.out.println("y=" + y);
            System.out.println("z=" + z);
            float acceleration = (float) Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;

            if (acceleration > SHAKE_THRESHOLD) {
                generateRandomNumber();
                generateRandomNumber();
                generateRandomNumber();

            }

        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sensorManager permet d'accéder aux capteurs
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  // on prend l'accelerometre0
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME); //0,02 seconde entre chaque prise, convient aux jeux
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener, mAccelerometer);
    }
}
