package com.slumdogsustainable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.ui.AppBarConfiguration;


import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import Domain_Layer.Logro;
import Domain_Layer.User;
//import Persistence.Repository;
import Domain_Layer.User_has_Logro;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
import Persistence.User_LRepository;
import Presentacion_layer.IUEstadisticas;
import Presentacion_layer.IUMenu;
import Presentacion_layer.IURanking;
import Presentacion_layer.IUperfil;
import Presentacion_layer.IUuserLogin;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    public static MediaPlayer music; //MediaPlayer sonidos
    public static MediaPlayer background; //Mediaplayer fondo
    public static User user;
    public static Queue<Logro> logrosCompletados = new ArrayDeque<>();

    Button botonInicio;

    TextView nombreLogro, descripcionLogro;
    LinearLayout bocadilloLogro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        new Task().execute();

        new Thread(() -> {
            user = new UserRepository(SingletonConnection.getSingletonInstance()).getUserByUsername("prueba");
            addObservadores(user);
        }).start();
        mostrarLogros();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mostrarLogros();
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.mainmusic);
        MainActivity.music.start();
    }

    private class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try
            {

                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                user = u.getUserByUsername("prueba");

               runOnUiThread(new Runnable() {
                    public void run() {
                        if(user == null) {
                            Intent intent = new Intent(MainActivity.this, IUuserLogin.class);
                            startActivity(intent);
                            finish();
                        }
                        else {

                            music = MediaPlayer.create(getApplicationContext(), R.raw.mainmusic);
                            background = MediaPlayer.create(getApplicationContext(), R.raw.backgroundmusic);
                            background.setLooping(true);
                            music.setLooping(true);
                            music.start();
                            setContentView(R.layout.inicio);
                            botonInicio = findViewById(R.id.botonInicio);
                            botonInicio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, IUMenu.class);//ControllerPartidaPregunta.class);
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
    }

    public void clickEstadisticas(View view){
        Intent intent = new Intent(MainActivity.this, IUEstadisticas.class);
        startActivity(intent);
    }

    public void clickRanking(View view){
        Intent intent = new Intent(MainActivity.this, IURanking.class);
        startActivity(intent);
    }

    public void addObservadores(User u) {
        User_has_Logro l = new User_has_Logro("",-1);
        List<User_has_Logro> logros = l.getAllUserLogros(u);
        User_LRepository ULR = new User_LRepository(SingletonConnection.getSingletonInstance());

        for (User_has_Logro x : logros) {
            if (!x.isCompletado()) {
                u.agregarObservador(x);
                if (x.getEnlaceUsuarioLogro(u.getNickname(), x.getId_logro()) == null) ULR.guardar(x);
            }
        }
    }

    public void mostrarLogros() {
        nombreLogro = findViewById(R.id.nombreLogro);
        descripcionLogro = findViewById(R.id.descripcionLogro);
        bocadilloLogro = findViewById(R.id.bocadilloLogro);

        System.out.println("\t\tESTO ESTA EJECUTANDOSE");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        while(logrosCompletados.size() > 0) {
            Logro logro = logrosCompletados.poll();

            // CREAR BOCADILLO
            System.out.println("Logro a mostrar: " + logro.getNombre());

            Animation animacionDesaparecer = new AlphaAnimation(1.0f, 0.0f);
            animacionDesaparecer.setDuration(100);

            nombreLogro.setText(logro.getNombre());
            descripcionLogro.setText(logro.getDescripcion());
            bocadilloLogro.setVisibility(View.VISIBLE);
            bocadilloLogro.setOnClickListener((View v) -> {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bocadilloLogro.setVisibility(View.INVISIBLE);
                        bocadilloLogro.startAnimation(animacionDesaparecer);
                    }
                }, 1000);
            });
        }
    }
}