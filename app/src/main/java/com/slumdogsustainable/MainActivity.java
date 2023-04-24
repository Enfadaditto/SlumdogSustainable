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

import com.j256.ormlite.support.ConnectionSource;


import Domain_Layer.User;
//import Persistence.Repository;
import Persistence.UserRepository;
import Presentacion_layer.IUEstadisticas;
import Presentacion_layer.IUperfil;
import Presentacion_layer.IUretoAhorcado;
import Presentacion_layer.IUuserLogin;
import Presentacion_layer.MediadorDeRetos;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    public static ConnectionSource conexion; //Conexion a DB
    public static MediaPlayer music; //MediaPlayer sonidos
    public static User user;

    Button botonInicio;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        new Task().execute();
    }



    private class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                if(conexion == null) {
                    UserRepository u = new UserRepository();
                    user = u.getUserByUsername("prueba");
                    conexion = u.getConnectionSource();
                }
               runOnUiThread(new Runnable() {
                    public void run() {
                        if(user == null) {
                            Intent intent = new Intent(MainActivity.this, IUuserLogin.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            music = MediaPlayer.create(getApplicationContext(), R.raw.mainmusic);
                            music.setLooping(true);
                            music.start();
                            setContentView(R.layout.inicio);
                            botonInicio = findViewById(R.id.botonInicio);
                            botonInicio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    music.stop();
                                    Intent intent = new Intent(MainActivity.this, MediadorDeRetos.class);//ControllerPartidaPregunta.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return null;
        }
    }

    public void clickModificarPerfil(View view){
        Intent intent = new Intent(MainActivity.this, IUperfil.class);
        startActivity(intent);
        finish();
    }

    public void clickEstadisticas(View view){
        Intent intent = new Intent(MainActivity.this, IUEstadisticas.class);
        startActivity(intent);
    }

}