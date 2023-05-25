package com.slumdogsustainable;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import Patron_Fabrica.BocadilloLogro;
import Patron_Fabrica.FabricaConcreta;
import Domain_Layer.Logro;
import Domain_Layer.User;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
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
    public static Queue<Logro> logrosCompletados;

    Button botonInicio;

    LinearLayout bocadilloLogro;

    public static final String[] PERMISSIONS = new String[]
            {
                    Manifest.permission.ACCESS_MEDIA_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        File existe = new File(getApplicationContext().getApplicationInfo().dataDir + "/files/usuarioActual.txt");
        if (!existe.exists()) crearArchivoSesion();

        new Task().execute();
        /*
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
        */
        logrosCompletados = new ArrayDeque<>();
    }

    private void crearArchivoSesion() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso de lectura");
        } else {
            Log.i("Mensaje", "Se tiene permiso de lectura y escritura");
            File fichero = new File(getApplicationContext().getApplicationInfo().dataDir + "/files/usuarioActual.txt");
            if (!fichero.exists()) {
                try {
                    fichero.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

                String nombreUsuario = getNombreSesion();

                user = User.getUserByUsername(u, nombreUsuario);

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
                            List<Integer> imageIds = Arrays.asList(R.drawable.icon1, R.drawable.icon2, R.drawable.icon3);
                            imagenUser = findViewById(R.id.botonPerfil);
                            imagenUser.setImageResource(imageIds.get(user.getIcon()));
                            nivelJugador = findViewById(R.id.TextoNivelUsuario);
                            nivelJugador.setText("Nivel "+ user.getNivelUsuario());

                            new Thread(() -> {
                                if (!user.isLogrosAñadidos()) {
                                    addObservadores(user);
                                    user.setLogrosAñadidos(true);
                                    u.actualizar(user);
                                    user.desbloquearLogro(new Logro().getLogroPorID(1));
                                    onWindowFocusChanged(true);
                                }
                            }).start();

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
                e.printStackTrace();
            }


            return null;
        }
    }

    private String getNombreSesion() throws IOException {
        String s = getApplicationContext().getApplicationInfo().dataDir;
        File usuarioActual = new File(s + "/files/usuarioActual.txt");

        BufferedReader bufferLector = new BufferedReader(new FileReader(usuarioActual));
        String nombreUsuario = bufferLector.readLine();
        bufferLector.close();

        System.out.println("Contenido del archivo " + usuarioActual.getName() + ": " + nombreUsuario);
        System.out.println(nombreUsuario);
        return nombreUsuario;
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
        System.out.println("\tMOSTRANDO LOGROS POR PANTALLA");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        while(logrosCompletados.size() > 0) {
            Logro logro = logrosCompletados.poll();

            System.out.println("Logro a mostrar: " + logro.getNombre());

            Animation animacionDesaparecer = new AlphaAnimation(1.0f, 0.0f);
            animacionDesaparecer.setDuration(50);
            Animation animacionAparecer = new AlphaAnimation(0.0f, 1.0f);
            animacionAparecer.setDuration(50);

            BocadilloLogro nuevoBocadillo = fabricaBocadillos.crearProducto(this, logro);
            nuevoBocadillo.setLayoutParams(bocadilloLogro.getLayoutParams());
            ViewGroup rootView = findViewById(R.id.content);
            rootView.addView(nuevoBocadillo);

            nuevoBocadillo.setVisibility(View.VISIBLE);
            nuevoBocadillo.startAnimation(animacionAparecer);
            nuevoBocadillo.setOnClickListener((View v) -> {
                        nuevoBocadillo.setVisibility(View.INVISIBLE);
                        nuevoBocadillo.startAnimation(animacionDesaparecer);
            });
            System.out.println("MOSTRADO BOCADILLO LOGRO: " + logro.getId_logro());
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
            Thread hilo = new Thread(() -> {
                user.desbloquearLogro(new Logro().getLogroPorID(29)); });
            hilo.start();
            try { hilo.join(); } catch (InterruptedException e) { e.printStackTrace(); }

            this.onWindowFocusChanged(true);
        }
    }

    public void addObservadores(User u) { u.addEnlaces(); }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) return;
        System.out.println("Tamaño cola logros: " + logrosCompletados.size());

        if (!logrosCompletados.isEmpty())
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mostrarLogros();
                }
            });

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(user != null) {
            List<Integer> imageIds = Arrays.asList(R.drawable.icon1, R.drawable.icon2, R.drawable.icon3);
            imagenUser = findViewById(R.id.botonPerfil);
            //imagenUser.setImageResource(imageIds.get(user.getIcon()));
        }
    }
}