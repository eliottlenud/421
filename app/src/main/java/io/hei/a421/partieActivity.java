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
import android.util.Log;
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
import io.hei.a421.Models.Joueur;

public class partieActivity extends AppCompatActivity {

    public static final Random RANDOM = new Random();
    public ImageView imageView1, imageView2, imageView3; //Les 3 images des dès
    public TextView nomJoueurActuel, numberofjetons; //Nom du joueur qui joue
    public int rangJoueur; //Pour parcourir la liste des joueurs
    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;
    public ArrayList<Joueur> partieList;
    private static int SHAKE_THRESHOLD = 10;
    public boolean moveit = true;
    public int nbclick = 0;
    MediaPlayer sonDe;
    String TAG = "partieActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        LinearLayout rangee =  findViewById(R.id.rangee);
        Button finDuTour =  findViewById((R.id.finDuTour));
        final Button relancer =  findViewById((R.id.relancer));

        //Définition des 3 dés + Nom joueur
        imageView1 =  findViewById(R.id.imageView1);
        imageView2 =  findViewById(R.id.imageView2);
        imageView3 =  findViewById(R.id.imageView3);
        nomJoueurActuel =  findViewById(R.id.nomJoueurActuel);
        numberofjetons = findViewById(R.id.numberofjetons);

        //sonDe = MediaPlayer.create(this, R.raw.son);

        //Sensor
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  // on prend l'accelerometre
        Intent intent = getIntent();
        partieList = intent.getParcelableArrayListExtra("partieList");

        //Definir le nombre de joueur, et le nombre de jetons
        int nbJoueur=partieList.size();
        int nbJetons;
        nbJetons=21/nbJoueur;
        //Distribuer le nombre de jetons au debut de la partie
        for (int i=0; i<nbJoueur; i++){
            partieList.get(i).setNbJetons(nbJetons);
        }
        //Récupération de la partieList

        nomJoueurActuel.setText(partieList.get(0).getPseudo());
        Log.d(TAG," oui cest : "+partieList.get(0).getNbJetons());
        numberofjetons.setText("" + partieList.get(0).getNbJetons());
        rangJoueur=0;






/*---------TEST EN CLIQUANT SUR LES DES----------*/
//Lancer les dés
        rangee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sonDe.start();
                moveit = false; //On met en pause la possibilité de relancer
                final Animation anim1 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);

                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value = randomDiceValue();
                        int value2 = randomDiceValue();
                        int value3 = randomDiceValue();
                        int temp;

                        //Trie les dés dans l'ordre décroissant
                        while (value<value2 || value2<value3 || value<value3) {
                            if (value<value2) {
                                temp = value;
                                value = value2;
                                value2 = temp;
                            }
                            if (value2<value3) {
                                temp = value2;
                                value2 = value3;
                                value3 = temp;
                            }
                        }

                        int res = getResources().getIdentifier("dice_" + value, "drawable", "io.hei.a421");
                        int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "io.hei.a421");
                        int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "io.hei.a421");

                        imageView1.setImageResource(res);
                        imageView2.setImageResource(res2);
                        imageView3.setImageResource(res3);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);

                imageView1.startAnimation(anim1);
                imageView2.startAnimation(anim1);
                imageView3.startAnimation(anim1);
            }
        });

        //Bouton qui passe au joueur suivant
        finDuTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbclick= 0;
                moveit = true;
                relancer.setVisibility(View.VISIBLE);
                onResume();
                rangJoueur ++;
                if (rangJoueur == partieList.size()){
                    rangJoueur=0;
                }
                nomJoueurActuel.setText(partieList.get(rangJoueur).getPseudo());
                numberofjetons.setText(""+partieList.get(rangJoueur).getNbJetons());
            }

        });

        //Bouton qui permet au joueur actuel de relancer (max 3 lancers)
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

/*--------------------------------------------TEST AVEC SHAKE TELEPHONE----------------------------------------------------*/
    final SensorEventListener mSensorEventListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            final Animation anim1 = AnimationUtils.loadAnimation(partieActivity.this, R.anim.shake);

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
                        int value2 = randomDiceValue();
                        int value3 = randomDiceValue();
                        int temp;

                        //Trie les dés dans l'ordre décroissant
                        while (value<value2 || value2<value3 || value<value3) {
                            if (value<value2) {
                                temp = value;
                                value = value2;
                                value2 = temp;
                            }
                            if (value2<value3) {
                                temp = value2;
                                value2 = value3;
                                value3 = temp;
                            }
                        }

                        int res = getResources().getIdentifier("dice_" + value, "drawable", "io.hei.a421");
                        int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "io.hei.a421");
                        int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "io.hei.a421");

                        imageView1.setImageResource(res);
                        imageView2.setImageResource(res2);
                        imageView3.setImageResource(res3);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);

                imageView1.startAnimation(anim1);
                imageView2.startAnimation(anim1);
                imageView3.startAnimation(anim1);

            }

        }

    };

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

    public void shakeoupasshake() {
        if (!moveit) {

            onPause();
        }
    }

}
