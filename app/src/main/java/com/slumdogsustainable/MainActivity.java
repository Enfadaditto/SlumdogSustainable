package com.slumdogsustainable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.ui.AppBarConfiguration;

import com.slumdogsustainable.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Domain_Layer.Question;
import Domain_Layer.User;
//import Persistence.Repository;
import Persistence.QuestionRepository;
import Persistence.UserRepository;
import Presentacion_layer.IUretoPregunta;
import Presentacion_layer.IUuserLogin;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    Button botonInicio;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        new Task().execute();
        
        botonInicio = (Button) findViewById(R.id.botonInicio);
       botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, IUretoPregunta.class);
                startActivity(intent);
            }
        });




    }



    private class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                MediaPlayer music = MediaPlayer.create(getApplicationContext(), R.raw.mainmusic);
                music.setLooping(true);
                music.start();

                //prueba Questions
                QuestionRepository f = new QuestionRepository();

                List<Question> prueba2 = f.getQuestionListByDifficulty("Baja");
                for(Question q : prueba2) {
                    System.out.println(q.getStatement());
                }

            }
            catch(Exception e)


            {

                System.out.println(e);

            }
            return null;
        }
    }

}