package io.hei.a421;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import io.hei.a421.Models.Joueur;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> partie;
    ArrayList<Joueur> partieList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText editText;
    Button buttonAdd;
    Button buttonJouer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.teamView);
        editText = findViewById(R.id.edit);
        buttonAdd = findViewById(R.id.ajouter);
        buttonJouer = findViewById(R.id.jouer);

        partie = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.player_view_layout,partie);
        listView.setAdapter(arrayAdapter);
        buttonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTeam();
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
            Log.d("Main",""+partieList.get(i).toString());
        }
    }
}
