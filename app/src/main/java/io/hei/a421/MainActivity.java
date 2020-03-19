package io.hei.a421;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.hei.a421.Models.Joueur;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> partie;
    public ArrayList<Joueur> partieList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText editText;
    Button buttonAdd, buttonJouer, buttonRegles, buttonReset;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.teamView);
        editText = findViewById(R.id.edit);
        buttonAdd = findViewById(R.id.ajouter);
        buttonJouer = findViewById(R.id.jouer);
        buttonRegles = findViewById(R.id.regles);
        buttonReset = findViewById(R.id.reset);

        partie = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.player_box, R.id.pseudo, partie);
        listView.setAdapter(arrayAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()!=0){
                    addPlayerToTeam(v);
                }
                else {
                    Log.d("coucou2","ca rentre la2");
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Vous devez rentrer un pseudo !");
                    alertDialogBuilder.setPositiveButton("Compris !", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"mkay bro",
                                    Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        buttonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partie.size()>=2){
                createTeam();
                Intent intent = new Intent(MainActivity.this, partieActivity.class);
                intent.putParcelableArrayListExtra("partieList", partieList);
                startActivity(intent);}
                else {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Veuillez rentrez au moins 2 joueurs.");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        buttonRegles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test","MenuRegles - intent");
                Intent intent = new Intent(MainActivity.this, menuRegles.class);
                startActivity(intent);
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTeam();
            }
        });
    }

    public void addPlayerToTeam(View v){
        partie.add(editText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        editText.setText("");
    }

    public void createTeam(){
        for(int i=0;i<partie.size();i++){
            Joueur jtemp = new Joueur(i, partie.get(i));
            partieList.add(jtemp);
            Log.d(TAG,""+partieList.get(i).toString());
        }
    }

    public void resetTeam(){
        int i=0;
        partie.clear();
        arrayAdapter.notifyDataSetChanged();
    }
}
