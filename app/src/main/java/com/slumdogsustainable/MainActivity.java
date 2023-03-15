package com.slumdogsustainable;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.slumdogsustainable.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import Builder.CreadorDeJuego;
import Builder.Juego;
import Builder.JuegoBuilder;
import Builder.JuegoRetoPregunta;
import Domain_Layer.User;
import Persistence.Repository;
import Persistence.UserRepository;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);
        new Task().execute();

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
                UserRepository s = new UserRepository();
                List<User> listaprueba = new ArrayList<>();
                listaprueba = s.obtenerTodos();
                for(User u : listaprueba) {
                   System.out.println(u.getNickname());
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