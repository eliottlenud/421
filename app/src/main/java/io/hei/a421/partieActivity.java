package io.hei.a421;

import android.app.AppComponentFactory;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class partieActivity extends AppCompatActivity {

    public static final Random RANDOM = new Random();
    public ImageView imageView1, imageView2, imageView3; //Les 3 images des dès
    public ArrayList<String> listeJoueurs = new ArrayList<>(); //La liste des joueurs actifs
    public TextView nomJoueurActuel; //Nom du joueur qui joue
    public int rangJoueur; //Pour parcourir la liste des joueurs
    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;
    private static int SHAKE_THRESHOLD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        LinearLayout rangee = (LinearLayout) findViewById(R.id.rangee);
        Button finDuTour = (Button) findViewById((R.id.finDuTour));
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        nomJoueurActuel = (TextView) findViewById(R.id.nomJoueurActuel);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  // on prend l'accelerometre

        listeJoueurs.add("Juliette");
        listeJoueurs.add("Eliott");
        listeJoueurs.add("Le gros Virg");
        listeJoueurs.add("Asmax");
        nomJoueurActuel.setText(listeJoueurs.get(0));
        rangJoueur=0;


/*
        rangee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value1 = randomDiceValue();
                int value2 = randomDiceValue();
                int value3 = randomDiceValue();

                int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.rollthedice");
                int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.rollthedice");
                int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.rollthedice");

                imageView1.setImageResource(res1);
                imageView2.setImageResource(res2);
                imageView3.setImageResource(res3);
            }
        });*/

        finDuTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangJoueur ++;
                if (rangJoueur == listeJoueurs.size()){
                    rangJoueur=0;
                }
                nomJoueurActuel.setText(listeJoueurs.get(rangJoueur));
            }

        });


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

    final SensorEventListener mSensorEventListener = new SensorEventListener() {

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
                int value1 = randomDiceValue();
                int value2 = randomDiceValue();
                int value3 = randomDiceValue();

                int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "io.hei.a421");
                int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "io.hei.a421");
                int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "io.hei.a421");

                imageView1.setImageResource(res1);
                imageView2.setImageResource(res2);
                imageView3.setImageResource(res3);

            }

        }

    };

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

}
