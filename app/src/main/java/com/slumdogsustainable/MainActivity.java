package com.slumdogsustainable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.ui.AppBarConfiguration;


import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import Domain_Layer.Logro;
import java.text.SimpleDateFormat;
import java.util.Date;

import Domain_Layer.User;
//import Persistence.Repository;
import Domain_Layer.User_has_Logro;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
import Persistence.User_LRepository;
import Presentacion_layer.IUEstadisticas;
import Presentacion_layer.IUMenu;
import Presentacion_layer.IURanking;
import Presentacion_layer.IUlogros;
import Presentacion_layer.IUperfil;
import Presentacion_layer.IUuserLogin;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    public static MediaPlayer music; //MediaPlayer sonidos
    public static MediaPlayer background; //Mediaplayer fondo
    public static User user;
    TextView nivelJugador;
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


        Date fecha = new Date("08/07/2023");

        Calendar diaDelaSemana = Calendar.getInstance();

        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        DayOfWeek dayOfWeek = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dayOfWeek = currentDate.getDayOfWeek();
        }
        System.out.println("------------" + dayOfWeek + "-------------------------");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
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
                            nivelJugador = findViewById(R.id.TextoNivelUsuario);
                            nivelJugador.setText("Nivel "+ user.getNivelUsuario());

                            nivelJugador.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });

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
    public void clickLogros(View view){
        Intent intent = new Intent(MainActivity.this, IUlogros.class);
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

    public void mostrarLogros() {
        nombreLogro = findViewById(R.id.nombreLogro);
        descripcionLogro = findViewById(R.id.descripcionLogro);
        bocadilloLogro = findViewById(R.id.bocadilloLogro);

        System.out.println("\tMOSTRANDO LOGROS POR PANTALLA");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Tamaño de la cola de logros: " + logrosCompletados.size());
        while(logrosCompletados.size() > 0) {
            Logro logro = logrosCompletados.poll();
            // CREAR BOCADILLO
            System.out.println("Logro a mostrar: " + logro.getNombre());

            Animation animacionDesaparecer = new AlphaAnimation(1.0f, 0.0f);
            animacionDesaparecer.setDuration(50);
            Animation animacionAparecer = new AlphaAnimation(0.0f, 1.0f);
            animacionAparecer.setDuration(50);

            nombreLogro.setText(logro.getNombre());
            descripcionLogro.setText(logro.getDescripcion());

            bocadilloLogro.setVisibility(View.VISIBLE);
            bocadilloLogro.startAnimation(animacionAparecer);
            bocadilloLogro.setOnClickListener((View v) -> {
                        bocadilloLogro.setVisibility(View.INVISIBLE);
                        bocadilloLogro.startAnimation(animacionDesaparecer);
            });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if(hasFocus)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mostrarLogros();
                }
            });
    }


}