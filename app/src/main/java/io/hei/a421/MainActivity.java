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
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText editText;
    Button buttonAdd;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.teamView);
        editText = findViewById(R.id.edit);
        buttonAdd = findViewById(R.id.ajouter);

        partie = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.player_view_layout,partie);
        listView.setAdapter(arrayAdapter);

    }

    public void addPlayerToTeam(View v){
        partie.add(editText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        editText.setText("");
    }
}
