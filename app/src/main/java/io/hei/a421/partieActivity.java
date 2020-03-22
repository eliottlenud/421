package io.hei.a421;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import io.hei.a421.Models.Joueur;

public class partieActivity extends AppCompatActivity {

    /*--------------------------------------------DECLARATION DES VARIABLES------------------------------------------------*/
    public static final Random RANDOM = new Random();
    public ImageView imageView1, imageView2, imageView3;        //Les 3 images des dés
    public TextView nomJoueurActuel, numberofjetons, topScore, minScore, nomBest, nomPire;  //Nom du joueur qui joue
    public int rangJoueur;
    public Button relancer;
    //Pour parcourir la liste des joueurs
    private SensorManager mSensorManager = null;                //Pour lancer les dés en secouant l'appareil
    private Sensor mAccelerometer = null;
    private static int SHAKE_THRESHOLD = 10;
    public ArrayList<Joueur> partieList;                        //Liste des joueurs de la partie
    public ArrayList<Joueur> tempList = new ArrayList<>();      //Liste temp des joueurs de la partie
    public boolean moveit = true;                               //Pour mettre en pause entre 2 lancers
    public int nbclick = 0;                                     //Gère le nombre de lancers par personne
    int a=0,b=0,c=0;                                            //Gère le verrouillage des dés (à ne pas relancer)
    boolean verrouillage_1, verrouillage_2, verrouillage_3,verDef_1,verDef_2,verDef_3;
    int value, value2, value3, score;                                  //Les chiffres composant le score
    MediaPlayer sonDe;
    String TAG = "partieActivity";
    List<Integer> listeScore = Tableau.listeScores;             //Liste et tableau des scores possibles avec nb de jetons correspondant
    int[][] tableau = Tableau.tableau;
    ArrayList <Integer> listeIndex = new ArrayList<>();         //Liste qui va servir à regarder meilleur/pire score
    public Joueur pireJoueur, bestJoueur;


    /*-------------------------------------------------A L'OUVERTURE DU JEU-------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        Button finDuTour =  findViewById((R.id.finDuTour));
        relancer = findViewById((R.id.relancer));
        relancer.setVisibility(View.INVISIBLE);

        //Définition des 3 dés + Nom joueur + Nb de jetons du joueur
        imageView1 =  findViewById(R.id.imageView1);
        imageView2 =  findViewById(R.id.imageView2);
        imageView3 =  findViewById(R.id.imageView3);
        nomJoueurActuel =  findViewById(R.id.nomJoueurActuel);
        numberofjetons = findViewById(R.id.numberofjetons);

        //Définition des scores et des noms correspondants
        topScore = findViewById(R.id.topScore);
        minScore = findViewById(R.id.toBeatScore);
        nomBest = findViewById(R.id.nameBest);
        nomPire = findViewById(R.id.nameWorst);

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
                    imageView1.setColorFilter(R.color.black);
                    verrouillage_1 =true;
                    a=1;
                    if (verrouillage_1==true && verrouillage_2==true && verrouillage_3==true){
                        relancer.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    imageView1.setColorFilter(Color.TRANSPARENT);
                    if(nbclick!=0){relancer.setVisibility(View.VISIBLE);}
                    verrouillage_1 =false;
                    a=0;

                }
            }
        });

        //verrouiller le dé 2
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b==0  && nbclick!=0) {
                    imageView2.setColorFilter(R.color.black);
                    verrouillage_2 =true;
                    b=1;
                    if (verrouillage_1==true && verrouillage_2==true && verrouillage_3==true){
                        relancer.setVisibility(View.INVISIBLE);
                    }
                }else {
                    imageView2.setColorFilter(Color.TRANSPARENT);
                    verrouillage_2 =false;
                    if(nbclick!=0){relancer.setVisibility(View.VISIBLE);}
                    b=0;
                }
            }
        });

        //verrouiller le dé 3
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c==0 && nbclick!=0) {
                    imageView3.setColorFilter(R.color.black);
                    verrouillage_3 =true;
                    c=1;
                    if (verrouillage_1==true && verrouillage_2==true && verrouillage_3==true){
                        relancer.setVisibility(View.INVISIBLE);
                    }
                }else {
                    imageView3.setColorFilter(Color.TRANSPARENT);
                    verrouillage_3 =false;
                    if(nbclick!=0){relancer.setVisibility(View.VISIBLE);}
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
                    resetTour();
                }
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
                moveit = false; //Une fois secoué, on ne peut plus relancer les dés sans appuyer sur "Relancer"
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //Si un des dés est "verrouillé" par le joueur, on garde sa valeur
                        if (verrouillage_1 ==false){ value = randomDiceValue();}
                        if (verrouillage_2 ==false){ value2 = randomDiceValue();}
                        if (verrouillage_3 ==false){ value3 = randomDiceValue();}

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

                        score = value*100 + value2 *10 + value3; //Enregistrement du score

                        //Change les images des dés correspondant dans l'ordre croissant
                        int res1 = getResources().getIdentifier("dice_" + value, "drawable", "io.hei.a421");
                        int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "io.hei.a421");
                        int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "io.hei.a421");
                        imageView1.setImageResource(res1);
                        imageView2.setImageResource(res2);
                        imageView3.setImageResource(res3);

                        //Remet à 0 le verrouillage des dés
                        a = 0;
                        b = 0;
                        c = 0;
                        verrouillage_1 = false;
                        verrouillage_2 = false;
                        verrouillage_3 = false;
                        imageView1.setColorFilter(Color.TRANSPARENT);
                        imageView2.setColorFilter(Color.TRANSPARENT);
                        imageView3.setColorFilter(Color.TRANSPARENT);

                        relancer.setVisibility(View.VISIBLE); //Affiche le bouton pour que le joueur puisse relancer

                        nbclick ++; //Calcule le nombre de lancers du joueur
                        if (nbclick==3){
                            resetTour();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                anim1.setAnimationListener(animationListener);
                if(verrouillage_1 ==false){imageView1.startAnimation(anim1);}
                if(verrouillage_2 ==false){imageView2.startAnimation(anim1);}
                if(verrouillage_3 ==false){imageView3.startAnimation(anim1);}

            }
        }
    };

    /*---------------------------------------------FONCTIONS UTILISEES---------------------------------------------------*/
    //Random entre 1 et 6 pour les dés
    public int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

    //Empêche de relancer sans faire exprès
    public void shakeoupasshake() {
        if (!moveit) {
            onPause();
        }
    }

    //Finit le tour du joueur
    public void resetTour() {
        relancer.setVisibility(View.INVISIBLE); //Efface le bouton relancer
        nbclick= 0; //Remet le nombre de lancers à 0
        moveit = true; //Permet au joueur de lancer
        onResume();
        listeIndex.add(Tableau.listeScores.indexOf(score));
        rangJoueur ++;
        if (rangJoueur == partieList.size()){
            newTour();
        }else {
            imageView1.setColorFilter(Color.TRANSPARENT);
            imageView2.setColorFilter(Color.TRANSPARENT);
            imageView3.setColorFilter(Color.TRANSPARENT);
            verrouillage_1 = false;
            verrouillage_2 = false;
            verrouillage_3 = false;
            nomJoueurActuel.setText(partieList.get(rangJoueur).getPseudo());
            numberofjetons.setText("" + partieList.get(rangJoueur).getNbJetons());
            bestJoueur = partieList.get(listeIndex.indexOf(Collections.min(listeIndex))); //Définit le pire joueur
            pireJoueur = partieList.get(listeIndex.indexOf(Collections.max(listeIndex))); //Définit le meilleur joueur
            topScore.setText("" + Tableau.listeScores.get(Collections.min(listeIndex))); //Ecrit le score qui gagne
            minScore.setText("" + Tableau.listeScores.get(Collections.max(listeIndex))); //Ecrit le score qui perd
            nomBest.setText("" + bestJoueur.getPseudo()); //Affiche le pseudo du meilleur joueur
            nomPire.setText("" + pireJoueur.getPseudo()); //Affiche le pseudo du pire joueur
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(partieActivity.this);
            alertDialogBuilder.setMessage("Votre score est de : " + value + value2 + value3 + ". Veuillez passer au joueur suivant.");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    //Fin du tour entier
    public void newTour() {
        bestJoueur = partieList.get(listeIndex.indexOf(Collections.min(listeIndex))); //Définit le pire joueur
        pireJoueur = partieList.get(listeIndex.indexOf(Collections.max(listeIndex))); //Définit le meilleur joueur
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(partieActivity.this);
        alertDialogBuilder.setMessage("Fin du tour !\n"+bestJoueur.getPseudo()+" donne "+tableau[Collections.min(listeIndex)][1]+" à "+pireJoueur.getPseudo());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        bestJoueur.setNbJetons(bestJoueur.getNbJetons() - tableau[Collections.min(listeIndex)][1]);
        pireJoueur.setNbJetons(pireJoueur.getNbJetons() + tableau[Collections.min(listeIndex)][1]);
        if (bestJoueur.getNbJetons() <= 0) {
            partieList.remove(listeIndex.indexOf(Collections.min(listeIndex)));
            listeIndex.remove(listeIndex.indexOf(Collections.min(listeIndex)));
        }

        if (partieList.size()> 1) {
            for (int i = listeIndex.indexOf(Collections.max(listeIndex)); i < listeIndex.size(); i++) {
                System.out.println("PartieList rang "+i+" = "+partieList.get(i).getPseudo());
                tempList.add(partieList.get(i));
            }
            for (int i = 0; i < listeIndex.indexOf(Collections.max(listeIndex)); i++) {
                tempList.add(partieList.get(i));
            }
            partieList.clear();
            for (int i = 0; i < tempList.size(); i++) {
                partieList.add(tempList.get(i));
            }
            tempList.clear();
            listeIndex.clear();
            rangJoueur = 0;
            imageView1.setColorFilter(Color.TRANSPARENT);
            imageView2.setColorFilter(Color.TRANSPARENT);
            imageView3.setColorFilter(Color.TRANSPARENT);
            verrouillage_1 = false;
            verrouillage_2 = false;
            verrouillage_3 = false;
            nomJoueurActuel.setText(partieList.get(rangJoueur).getPseudo());
            numberofjetons.setText("" + partieList.get(rangJoueur).getNbJetons());
            topScore.setText(""); //Ecrit le score qui gagne
            minScore.setText(""); //Ecrit le score qui perd
            nomBest.setText(""); //Affiche le pseudo du meilleur joueur
            nomPire.setText(""); //Affiche le pseudo du pire joueur
        } else {
            final AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(partieActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            alertDialogBuilder2.setTitle("Fin de partie");
            alertDialogBuilder2.setMessage("Partie finie !\n"+pireJoueur.getPseudo()+" a perdu !");
            alertDialogBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(partieActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog2 = alertDialogBuilder2.create();
            alertDialog2.show();
        }
    }
}
