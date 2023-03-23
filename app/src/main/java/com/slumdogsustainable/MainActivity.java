package com.slumdogsustainable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        new Task().execute();


        botonInicio = (Button) findViewById(R.id.botonInicio);
       botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, IUuserLogin.class);
                startActivity(intent);
            }
        });



        /*JuegoBuilder retoPegunta = new JuegoRetoPregunta();
        //Esto es lo que hace el director
       CreadorDeJuego creadorDeJuego = new CreadorDeJuego();
       creadorDeJuego.setJuegoBuilder(retoPegunta);


       creadorDeJuego.construirJuego();
       Juego juego = retoPegunta.getJuego();








       // creadorDeJuego.setJuegoBuilder(retoPegunta);

        creadorDeJuego.construirJuego();

      //  Juego juegoActual = creadorDeJuego.getJuego();
        +/





       /* binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/


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