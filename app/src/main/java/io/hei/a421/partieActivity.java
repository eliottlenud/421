package io.hei.a421;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import io.hei.a421.Models.Joueur;

public class partieActivity extends AppCompatActivity {

/*--------------------------------------------DECLARATION DES VARIABLES------------------------------------------------*/
    public static final Random RANDOM = new Random();
    public ImageView imageView1, imageView2, imageView3;        //Les 3 images des dés
    public TextView nomJoueurActuel, numberofjetons, topScore, minScore;  //Nom du joueur qui joue
    public int rangJoueur;
    public Button relancer,lancer;
    //Pour parcourir la liste des joueurs
    private SensorManager mSensorManager = null;                //Pour lancer les dés en secouant l'appareil
    private Sensor mAccelerometer = null;
    private static int SHAKE_THRESHOLD = 10;
    public ArrayList<Joueur> partieList;                        //Liste des joueurs de la partie
    public boolean moveit = true;                               //Pour mettre en pause entre 2 lancers
    public int nbclick = 0;                                     //Gère le nombre de lancers par personne
    int a=0,b=0,c=0;                                            //Gère le verrouillage des dés (à ne pas relancer)
    boolean verouillage_1,verouillage_2,verouillage_3;
    int value, value2, value3, score;                                  //Les chiffres composant le score
    MediaPlayer sonDe;
    String TAG = "partieActivity";
    List<Integer> listeScore = Tableau.listeScores;             //Liste et tableau des scores possibles avec nb de jetons correspondant
    int[][] tableau = Tableau.tableau;
    ArrayList <Integer> listeIndex = new ArrayList<>();         //Liste qui va servir à regarder meilleur/pire score


/*-------------------------------------------------A L'OUVERTURE DU JEU-------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        LinearLayout rangee =  findViewById(R.id.rangee);
        Button finDuTour =  findViewById((R.id.finDuTour));
        relancer = findViewById((R.id.relancer));
        relancer.setVisibility(View.INVISIBLE);

        //Définition des 3 dés + Nom joueur + Nb de jetons du joueur
        imageView1 =  findViewById(R.id.imageView1);
        imageView2 =  findViewById(R.id.imageView2);
        imageView3 =  findViewById(R.id.imageView3);
        nomJoueurActuel =  findViewById(R.id.nomJoueurActuel);
        numberofjetons = findViewById(R.id.numberofjetons);

        //Définition des scores
        topScore = findViewById(R.id.topScore);
        minScore = findViewById(R.id.toBeatScore);

        sonDe = MediaPlayer.create(this, R.raw.son);

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









/*------------------------------------------------------VERROUILLAGE DES DES--------------------------------------------------*/
        //verrouiller le dé 1
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a==0 && nbclick!=0) {
                    imageView1.setBackgroundColor(Color.RED);
                    verouillage_1 =true;
                    a=1;
                }else {
                    imageView1.setBackgroundColor(Color.TRANSPARENT);
                    verouillage_1 =false;
                    a=0;
                }
            }
        });

        //verrouiller le dé 2
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b==0  && nbclick!=0) {
                    imageView2.setBackgroundColor(Color.RED);
                    verouillage_2 =true;
                    b=1;
                }else {
                    imageView2.setBackgroundColor(Color.TRANSPARENT);
                    verouillage_2 =false;
                    b=0;
                }
            }
        });

        //verrouiller le dé 3
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c==0 && nbclick!=0) {
                    imageView3.setBackgroundColor(Color.RED);
                    verouillage_3 =true;
                    c=1;
                }else {
                    imageView3.setBackgroundColor(Color.TRANSPARENT);
                    verouillage_3 =false;
                    c=0;
                }
            }
        });


/*-----------------------------------------BOUTON FIN DU TOUR----------------------------------------------------*/
        //Bouton qui passe au joueur suivant
        finDuTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nbclick==0){
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(partieActivity.this);
                    alertDialogBuilder.setMessage("Veuillez lancer les dés au moins une fois.");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else {
                nbclick= 0;
                moveit = true;
                relancer.setVisibility(View.INVISIBLE);
                onResume();
                rangJoueur ++;
                if (rangJoueur == partieList.size()){
                    rangJoueur=0;
                }
                imageView1.setBackgroundColor(Color.TRANSPARENT);
                imageView2.setBackgroundColor(Color.TRANSPARENT);
                imageView3.setBackgroundColor(Color.TRANSPARENT);
                verouillage_1 = false;
                verouillage_2 = false;
                verouillage_3 = false;
                nomJoueurActuel.setText(partieList.get(rangJoueur).getPseudo());
                numberofjetons.setText(""+partieList.get(rangJoueur).getNbJetons());
                listeIndex.add(Tableau.listeScores.indexOf(score));
                topScore.setText(""+Tableau.listeScores.get(Collections.min(listeIndex)));
                minScore.setText(""+Tableau.listeScores.get(Collections.max(listeIndex)));
                System.out.println("La liste des scores est "+listeIndex);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(partieActivity.this);
                alertDialogBuilder.setMessage("Votre score est de : "+value+value2+value3+". Veuillez passer au joueur suivant.");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();}
            }

        });


/*-----------------------------------------BOUTON RELANCER----------------------------------------------------*/
        //Bouton qui permet au joueur actuel de relancer (max 3 lancers)
        relancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveit == false) {
                    moveit = true;
                    relancer.setVisibility(View.INVISIBLE);
                    onResume();
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

            float acceleration = (float) Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;
            shakeoupasshake();

            if (acceleration > SHAKE_THRESHOLD) {
                moveit = false;
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        System.out.println("Quand je secoue");
                        System.out.println("verrouillage 1 = "+verouillage_1);
                        System.out.println("verrouillage 2 = "+verouillage_2);
                        System.out.println("verrouillage 3 = "+verouillage_3);
                        if (verouillage_1==false){ value = randomDiceValue();}
                        if (verouillage_2==false){ value2 = randomDiceValue();}
                        if (verouillage_3==false){ value3 = randomDiceValue();}
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

                        System.out.println("Value 1 = "+value);
                        System.out.println("Value 2 = "+value2);
                        System.out.println("Value 3 = "+value3);
                        score = value*100 + value2 *10 + value3;

                        int res1 = getResources().getIdentifier("dice_" + value, "drawable", "io.hei.a421");
                        int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "io.hei.a421");
                        int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "io.hei.a421");

                        imageView1.setImageResource(res1);
                        imageView2.setImageResource(res2);
                        imageView3.setImageResource(res3);

                        a = 0;
                        b = 0;
                        c = 0;
                        verouillage_1 = false;
                        verouillage_2 = false;
                        verouillage_3 = false;
                        imageView1.setBackgroundColor(Color.TRANSPARENT);
                        imageView2.setBackgroundColor(Color.TRANSPARENT);
                        imageView3.setBackgroundColor(Color.TRANSPARENT);
                        nbclick ++;
                        relancer.setVisibility(View.VISIBLE);
                        System.out.println("nb de lancer : "+nbclick);
                        if (nbclick==3){
                            relancer.setVisibility(View.INVISIBLE);
                            nbclick= 0;
                            moveit = true;
                            onResume();
                            rangJoueur ++;
                            if (rangJoueur == partieList.size()){
                                rangJoueur=0;
                            }
                            imageView1.setBackgroundColor(Color.TRANSPARENT);
                            imageView2.setBackgroundColor(Color.TRANSPARENT);
                            imageView3.setBackgroundColor(Color.TRANSPARENT);
                            verouillage_1 = false;
                            verouillage_2 = false;
                            verouillage_3 = false;
                            nomJoueurActuel.setText(partieList.get(rangJoueur).getPseudo());
                            numberofjetons.setText(""+partieList.get(rangJoueur).getNbJetons());
                            listeIndex.add(Tableau.listeScores.indexOf(score));
                            topScore.setText(""+Tableau.listeScores.get(Collections.min(listeIndex)));
                            minScore.setText(""+Tableau.listeScores.get(Collections.max(listeIndex)));
                            System.out.println("La liste des scores est "+listeIndex);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(partieActivity.this);
                            alertDialogBuilder.setMessage("Votre score est de : "+value+value2+value3+". Veuillez passer au joueur suivant.");
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);
                if(verouillage_1==false){imageView1.startAnimation(anim1);}
                if(verouillage_2==false){imageView2.startAnimation(anim1);}
                if(verouillage_3==false){imageView3.startAnimation(anim1);}

            }
        }
    };

    public int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

    public void shakeoupasshake() {
        if (!moveit) {

            onPause();
        }
    }
}
