package com.slumdogsustainable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.navigation.ui.AppBarConfiguration;


import com.j256.ormlite.stmt.query.In;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import Patron_Fabrica.BocadilloLogro;
import Patron_Fabrica.FabricaConcreta;
import Domain_Layer.Logro;

import Domain_Layer.User;
//import Persistence.Repository;
import Patron_Mediador.MediadorLogros;
import Persistence.LogroRepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
import Patron_Fachada.FachadaDeRetos;
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
    public ImageView imagenUser;
    TextView nivelJugador;
    public static Queue<Logro> logrosCompletados = new ArrayDeque<>();
    List<Logro> logros;
    Button botonInicio;
    ConstraintLayout bocadilloLogro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_carga);
        new Task().execute();


    }



    @Override
    protected void onRestart() {
        super.onRestart();
        if(music != null) {
            music.stop();
            MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.mainmusic);
            MainActivity.music.start();}
    }

    private class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try
            {

                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                //user = User.getUserByUsername(u, "prueba");

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
                            List<Integer> imageIds = Arrays.asList(R.drawable.icon1, R.drawable.icon2, R.drawable.icon3);
                            imagenUser = findViewById(R.id.botonPerfil);
                            imagenUser.setImageResource(imageIds.get(user.getIcon()));
                            nivelJugador = findViewById(R.id.TextoNivelUsuario);
                            nivelJugador.setText("Nivel "+ user.getNivelUsuario());

                            MediadorLogros mediador = new MediadorLogros();
                            Thread hiloLogro = new Thread(() -> {
                                logros = new LogroRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
                            });
                            hiloLogro.start();
                            try { hiloLogro.join(); } catch (InterruptedException e) {e.printStackTrace();}
                            mediador.registrarUsuario(user);
                            mediador.registrarLogros(logros);

                            if (!user.isLogrosAñadidos()) {
                                System.out.println("PANTALLA DE CARGA");
                                Thread hilo = new Thread(() -> {
                                    mediador.addEnlacesToUser();
                                    user.desbloquearLogro(new Logro().getLogroPorID(1));
                                });
                                hilo.start();
                                try {hilo.join();} catch (InterruptedException e) {e.printStackTrace();}
                                setContentView(R.layout.inicio);

                                onWindowFocusChanged(true);
                            }



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
        bocadilloLogro = findViewById(R.id.bocadilloLogro);

        FabricaConcreta fabricaBocadillos = new FabricaConcreta();
        Toast.makeText(this,"¡Nuevo logro desbloqueado!", Toast.LENGTH_SHORT).show();

        System.out.println("\tMOSTRANDO LOGROS POR PANTALLA");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        crearBocadillo(fabricaBocadillos);
    }

    private void crearBocadillo(FabricaConcreta fabricaBocadillos) {
        int i = 0x12351614;
        while (!logrosCompletados.isEmpty()) {
            Logro logro = logrosCompletados.poll();
            System.out.println("Logro a mostrar: " + logro.getNombre());

            Animation animacionDesaparecer = new AlphaAnimation(1.0f, 0.0f);
            animacionDesaparecer.setDuration(50);
            Animation animacionAparecer = new AlphaAnimation(0.0f, 1.0f);
            animacionAparecer.setDuration(50);

            BocadilloLogro nuevoBocadillo = fabricaBocadillos.crearProducto(this, logro);
            nuevoBocadillo.setId(i);
            ConstraintLayout rootView = findViewById(R.id.content);
            rootView.addView(nuevoBocadillo);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootView);
            constraintSet.connect(i, ConstraintSet.START, rootView.getId(), ConstraintSet.START, 40);
            constraintSet.connect(i, ConstraintSet.END, rootView.getId(), ConstraintSet.END, 40);
            constraintSet.connect(i, ConstraintSet.TOP, botonInicio.getId(), ConstraintSet.BOTTOM, 10);
            constraintSet.connect(i, ConstraintSet.BOTTOM, imagenUser.getId(), ConstraintSet.TOP, 10);
            constraintSet.applyTo(rootView);

            nuevoBocadillo.setVisibility(View.VISIBLE);
            nuevoBocadillo.startAnimation(animacionAparecer);
            nuevoBocadillo.setOnClickListener((View v) -> {
                rootView.removeView(nuevoBocadillo);
            });
            System.out.println("MOSTRADO BOCADILLO LOGRO: " + logro.getId_logro());
            i++;
        }
    }

    int contadorToques = 0;
    public void easterEgg(View v){
        contadorToques++;
        if (contadorToques == 1)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                contadorToques = 0;
            }
            }, 3000);
        if (contadorToques == 5) {
            Thread hilo = new Thread( () -> {
                user.desbloquearLogro(new Logro().getLogroPorID(29));
            }); hilo.start();
            try { hilo.join(); } catch (InterruptedException e) { e.printStackTrace(); }

            onWindowFocusChanged(true);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) return;
        System.out.println("Tamaño cola logros: " + logrosCompletados.size());
        if (!logrosCompletados.isEmpty()) mostrarLogros();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(music != null) {
            music.stop();
            MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.mainmusic);
            MainActivity.music.start();}
        if(user != null) {
            List<Integer> imageIds = Arrays.asList(R.drawable.icon1, R.drawable.icon2, R.drawable.icon3);
            if(imagenUser!=null) {
                imagenUser.setImageResource(imageIds.get(user.getIcon()));
            }
        }
    }
}