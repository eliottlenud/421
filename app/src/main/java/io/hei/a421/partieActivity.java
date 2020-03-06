package io.hei.a421;

import android.app.AppComponentFactory;
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
    public ImageView imageView1, imageView2, imageView3;
    public ArrayList<String> listeJoueurs = new ArrayList<>();
    public TextView nomJoueurActuel;
    public int rangJoueur;

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

        listeJoueurs.add("Juliette");
        listeJoueurs.add("Eliott");
        listeJoueurs.add("Le gros Virg");
        listeJoueurs.add("Asmax");
        nomJoueurActuel.setText(listeJoueurs.get(0));
        rangJoueur=0;

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
        });

        finDuTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangJoueur ++;
                if (rangJoueur == listeJoueurs.size()){
                    rangJoueur=0;
                }
                System.out.println("rang joueur"+rangJoueur);
                System.out.println("size list"+listeJoueurs.size());

                nomJoueurActuel.setText(listeJoueurs.get(rangJoueur));
            }
        });


    }

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

}
