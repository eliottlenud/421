package io.hei.a421;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class menuRegles extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Test","MenuRegles - intent 0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reglesjeu_layout);

    button1 = findViewById(R.id.button1);
    button2 = findViewById(R.id.button2);
    button3= findViewById(R.id.button3);
    button4= findViewById(R.id.button4);
    button5= findViewById(R.id.button5);
    button6= findViewById(R.id.button6);
    Log.d("Test","MenuRegles");
    Log.d("Test",button1.toString());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.button1);
                Log.d("Test","Boutton 1");
                changeLayout();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.button2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.button3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.button4);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.button5);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.button6);
            }
        });
    }

    public void changeLayout(){
        setContentView(R.layout.button1);
        Log.d("Test","Bouton 1 - fct");
    }
}
