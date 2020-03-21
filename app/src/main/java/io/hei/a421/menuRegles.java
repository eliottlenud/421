package io.hei.a421;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import io.hei.a421.regles.regle1;
import io.hei.a421.regles.regle2;
import io.hei.a421.regles.regle3;
import io.hei.a421.regles.regle4;
import io.hei.a421.regles.regle5;
import io.hei.a421.regles.regle6;

public class menuRegles extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        Log.d("Test","MenuRegles - intent");
        Intent intent = new Intent(menuRegles.this, MainActivity.class);
        startActivity(intent);

    }

    Button button1, button2, button3, button4, button5, button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reglesjeu_layout);

    button1 = findViewById(R.id.button1);
    button2 = findViewById(R.id.button2);
    button3= findViewById(R.id.button3);
    button4= findViewById(R.id.button4);
    button5= findViewById(R.id.button5);
    button6= findViewById(R.id.button6);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuRegles.this, regle1.class);
                startActivity(intent);}
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuRegles.this, regle2.class);
                startActivity(intent);}

        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuRegles.this, regle3.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuRegles.this, regle4.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuRegles.this, regle5.class);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuRegles.this, regle6.class);
                startActivity(intent);
            }
        });
    }

    public void changeLayout(){
        setContentView(R.layout.button1);
        Log.d("Test","Bouton 1 - fct");
    }
}
