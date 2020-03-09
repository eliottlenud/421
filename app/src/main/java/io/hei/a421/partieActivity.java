package io.hei.a421;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private static int SHAKE_THRESHOLD = 10;
    public boolean moveit = true;
    public int nbclick = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        LinearLayout rangee = (LinearLayout) findViewById(R.id.rangee);
        Button finDuTour = (Button) findViewById((R.id.finDuTour));
        final Button relancer = (Button) findViewById((R.id.relancer));
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        nomJoueurActuel = (TextView) findViewById(R.id.nomJoueurActuel);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  // on prend l'accelerometre

        listeJoueurs.add("Juliette");
        listeJoueurs.add("Eliott");
        listeJoueurs.add("Le plus beau Virg");
        listeJoueurs.add("Asmax");
        nomJoueurActuel.setText(listeJoueurs.get(0));
        rangJoueur=0;

    //Definir le nombre de joueur
        int nbJoueur=0;
        nbJoueur=listeJoueurs.size();

     //Initialiser le nombre de jeton au debut
        /*  public int DistributionJetons(int idJoueur){
            int nbJetons;
            nbJetons=21/nbJoueur;
            return nbJetons;
        }
*/

//Distrubuer les 21 jetons
/*
        rangee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anim1 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);
                final Animation anim2 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);
                final Animation anim3 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);

                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value = randomDiceValue();
                        int res = getResources().getIdentifier("dice_" + value, "drawable", "io.hei.a421");

                        if (animation == anim1) {
                            imageView1.setImageResource(res);
                        } else if (animation == anim2) {
                            imageView2.setImageResource(res);
                        } else if (animation == anim3) {
                            imageView3.setImageResource(res);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);
                anim2.setAnimationListener(animationListener);
                anim3.setAnimationListener(animationListener);

                imageView1.startAnimation(anim1);
                imageView2.startAnimation(anim2);
                imageView3.startAnimation(anim3);
            }
        });
*/
        finDuTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbclick= 0;
                relancer.setVisibility(View.VISIBLE);
                moveit = true;
                onResume();
                rangJoueur ++;
                if (rangJoueur == listeJoueurs.size()){
                    rangJoueur=0;
                }
                nomJoueurActuel.setText(listeJoueurs.get(rangJoueur));
            }

        });

        relancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveit = true;

                onResume();
                nbclick= nbclick +1;
                if (nbclick == 2) {
                    relancer.setVisibility(View.INVISIBLE);
                }
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
            final Animation anim1 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);
            final Animation anim2 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);
            final Animation anim3 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);

            // Que faire en cas d'évènements sur le capteur ?
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            System.out.println("x=" + x);
            System.out.println("y=" + y);
            System.out.println("z=" + z);
            float acceleration = (float) Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;

            shakeoupasshake();

            if (acceleration > SHAKE_THRESHOLD) {
                moveit = false;
                int value1 = randomDiceValue();
                int value2 = randomDiceValue();
                int value3 = randomDiceValue();
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value = randomDiceValue();
                        int res = getResources().getIdentifier("dice_" + value, "drawable", "io.hei.a421");

                        if (animation == anim1) {
                            imageView1.setImageResource(res);
                        } else if (animation == anim2) {
                            imageView2.setImageResource(res);
                        } else if (animation == anim3) {
                            imageView3.setImageResource(res);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);
                anim2.setAnimationListener(animationListener);
                anim3.setAnimationListener(animationListener);

                imageView1.startAnimation(anim1);
                imageView2.startAnimation(anim2);
                imageView3.startAnimation(anim3);

            }

        }

    };

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

    public void shakeoupasshake() {
        if (moveit == false) {
            onPause();
        }
    }

}
