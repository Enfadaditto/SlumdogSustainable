package Presentacion_layer;

import static Presentacion_layer.MediadorDeRetos.ABANDON;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.j256.ormlite.stmt.query.In;
import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import org.w3c.dom.Text;

import java.io.SyncFailedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Domain_Layer.DFButton;
import Domain_Layer.Frase;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class IUretoFrase extends AppCompatActivity {
    LinearLayout letrasLayout;
    Frase frase;
    char[] fraseProblema;
    List<Character> listadoCaracteresFrase, listaLetrasMostradasPista = new ArrayList<>();
    Button casillaElegida;
    DFButton casillaElegidaSolucion;
    String fraseEnunciado, descripcionEnunciado;
    int PuntosTotales, PuntosConsolidados, Vidas, cantidadRetosContestados, TiempoOpcion, Tiempo,
            Nivel, SonidoFallo, SonidoAcierto, odsFrase, timeCount = 0;
    ProgressBar timeBar;
    CountDownTimer mCountDownTimer;
    RelativeLayout panelDescubrirFrase, pantalla_final, contenedor, contenedorMostrarPista;
    ImageView imagenPantallaFinal, vidasImage, imagenODS, abandonarIcon, acierto_fallo, imagenPista;
    TextView descripcionFrase, textoPuntosFinales, textoPuntosGanados, textoPuntosTotal, textoPuntuacionPregunta,
            textoPuntosConsolidados, textoPuntuacionTotal, contadorPistas, textoMostrarPista;
    Button botonConsolidar, botonSiguientePregunta, botonTerminar;
    boolean ultimaAcertada, haConsolidado, Acierto = false, Abandono,  Consolidado;

    Map<Button, Character> botonYSolucion = new HashMap<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        frase = new Frase("", 0, "");
        cargarDatos();

        letrasLayout = findViewById(R.id.letrasLayout);
        panelDescubrirFrase = findViewById(R.id.panelDescubrirFrase);
        descripcionFrase = findViewById(R.id.descripcionFrase);
        pantalla_final = findViewById(R.id.contenedor_final);
        imagenPantallaFinal = findViewById(R.id.imagenPantallaFinal);
        textoPuntosFinales = findViewById(R.id.textoPuntosFinales);
        textoPuntosGanados = findViewById(R.id.puntosGanados);
        textoPuntosTotal = findViewById(R.id.puntosTotal);
        botonConsolidar = findViewById(R.id.botonConsolidar);
        botonSiguientePregunta = findViewById(R.id.botonSiguientePregunta);
        acierto_fallo = findViewById(R.id.imagen_acierto);
        contenedor = findViewById(R.id.contenedor_resp);
        vidasImage = findViewById(R.id.imageView6);
        vidasImage.setVisibility(View.INVISIBLE);
        abandonarIcon = findViewById(R.id.imageView7);
        textoPuntosConsolidados = findViewById(R.id.textView3);
        textoPuntosConsolidados.setVisibility(View.INVISIBLE);
        textoPuntuacionPregunta = findViewById(R.id.textView);
        textoPuntuacionTotal = findViewById(R.id.textView2);
        imagenODS = findViewById(R.id.imageView2);
        botonTerminar = findViewById(R.id.botonTerminarPartida);
        imagenPista = findViewById(R.id.imagenPista);
        contadorPistas = findViewById(R.id.contadorBombillas);
        contenedorMostrarPista = findViewById(R.id.muestraPistaPanel);
        contenedorMostrarPista.setVisibility(View.INVISIBLE);
        textoMostrarPista = findViewById(R.id.textoMostrarPista);

        fraseProblema = frase.getFraseProblema(Nivel);
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);
        casillaElegida = new DFButton(this);

        rellenarLetrasLayout(listadoCaracteresFrase);
        letrasLayout.setVisibility(View.INVISIBLE);

        textoPuntuacionPregunta.setText("Por " + Nivel*100 + " puntos.");
        textoPuntuacionTotal.setText("Puntuacion total: " + PuntosTotales);
        descripcionFrase.setText("Haz click AQUI para comenzar");
        descripcionFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComenzarReto();
            }
        });
        casillaElegidaSolucion = new DFButton(new Button(this), '*');
    }

    public void cargarDatos() {
        Bundle extras = getIntent().getExtras();

        fraseEnunciado = extras.getString("fraseEnunciado");
        descripcionEnunciado = extras.getString("descripcionEnunciado");
        PuntosTotales = extras.getInt("PuntosTotales");
        PuntosConsolidados = extras.getInt("PuntosConsolidados");
        Vidas = extras.getInt("Vidas");
        cantidadRetosContestados = extras.getInt("Ronda");
        haConsolidado = extras.getBoolean("haConsolidado");
        Tiempo = extras.getInt("Tiempo");
        TiempoOpcion = extras.getInt("TiempoOpcion");
        Nivel = extras.getInt("Nivel");
        SonidoAcierto = extras.getInt("SonidoAcierto");
        SonidoFallo = extras.getInt("SonidoFallo");
        Tiempo = extras.getInt("Tiempo");
        odsFrase = extras.getInt("odsFrase");

        frase.setFrase(fraseEnunciado);
    }

    private void ComenzarReto() {
        for (int i = 0; i < panelDescubrirFrase.getChildCount(); i++) {
            ((Button) panelDescubrirFrase.getChildAt(i)).setText("");
            ((Button) panelDescubrirFrase.getChildAt(i)).setBackground(getDrawable(R.drawable.boton_panel_vacio));
        }
        poner_imagen_ods();
        ponerFrasePorPantalla();

        letrasLayout.setVisibility(View.VISIBLE);
        descripcionFrase.setText(descripcionEnunciado);
        descripcionFrase.setOnClickListener(null);

        vidasImage.setVisibility(View.VISIBLE);
        imagenODS.setVisibility(View.VISIBLE);
        imagenPista.setVisibility(View.VISIBLE);
        contadorPistas.setVisibility(View.VISIBLE);
        if (Vidas == 1) vidasImage.setImageDrawable(getDrawable(R.drawable.corazon_vida));
        if (Vidas == 0) vidasImage.setImageDrawable(getDrawable(R.drawable.corazon_roto));
        textoPuntuacionPregunta.setVisibility(View.VISIBLE);
        textoPuntuacionTotal.setVisibility(View.VISIBLE);
        if (haConsolidado)  {
            textoPuntosConsolidados.setText("Puntos consolidados: " + PuntosConsolidados + " puntos.");
            abandonarIcon.setVisibility(View.VISIBLE);
            textoPuntosConsolidados.setVisibility(View.VISIBLE);
        }
        if (MediadorDeRetos.pistas == 0) {
            imagenPista.setOnClickListener(null);
            imagenPista.setImageDrawable(getDrawable(R.drawable.pista2));
        }
        contadorPistas.setText(MediadorDeRetos.pistas + " / 3");

        startTimer(Tiempo);
    }

    private void rellenarLetrasLayout(List<Character> listado)  {
        for (char letra : listado) {
            Button boton = new Button(this);
            boton.setLayoutParams(letrasLayout.getLayoutParams());
            boton.setBackground(getDrawable(R.drawable.botones_teclado));
            boton.setText(letra+""); boton.setTextColor(Color.WHITE);
            boton.setTextSize(48);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) boton.getLayoutParams();
            float dp = getResources().getDisplayMetrics().density;
            int newMargin = (int) (2 * dp);
            layoutParams.setMargins(newMargin, 0, newMargin, 0);
            boton.setLayoutParams(layoutParams);

            boton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(comprobarFraseCorrecta((Button) v, casillaElegida, casillaElegidaSolucion)) {
                        letrasLayout.removeView(v);
                        ultimaAcertada = true;
                        if (letrasLayout.getChildCount() == 0) {
                            Acierto = true;
                            terminoBien();
                        }
                    }
                }
            });
            letrasLayout.addView(boton);
        }
    }

    private boolean comprobarFraseCorrecta(Button botonPulsado, Button casillaElegida, DFButton casillaElegidaSolucion) {
        char letraPulsada = botonPulsado.getText().charAt(0);
        char letraSolucion = casillaElegidaSolucion.getLetra();
        if (!(letraPulsada+"").toUpperCase().equals((letraSolucion+"").toUpperCase())) {
            //ERROR
            return false;
        }
        else {
            casillaElegida.setBackgroundColor(Color.LTGRAY);
            casillaElegida.setClickable(false);
            casillaElegida.setText(letraSolucion+"");
            casillaElegidaSolucion.setLetra('*');
            return true;
        }
    }

    private void ponerFrasePorPantalla() {
        int indice = 0;
        String[] palabras = String.valueOf(fraseProblema).split(" ");
        String[] palabrasSolucion = String.valueOf(frase.getFrase()).split(" ");;

        for (int i = 0; i < palabras.length; i++) {
            if (i == -1) return;
            System.out.println(palabras[i] + " :: " + palabrasSolucion[i]);
            indice = escribirPalabra(palabras[i].toCharArray(), palabrasSolucion[i].toCharArray(), indice);
            ++indice;
        }

    }

    private void poner_imagen_ods() {
        int imagenId = getResources().getIdentifier("ods_" + odsFrase, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(imagenId);
        imagenODS.setImageDrawable(imagen);
        imagenODS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getResources().getStringArray(R.array.linkODS)[odsFrase]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private int escribirPalabra(char[] palabraActual, char[] palabraActualSolucion, int indice) {
        if (((indice % (panelDescubrirFrase.getChildCount()/3)) + palabraActual.length) > (panelDescubrirFrase.getChildCount()/3) ) {
            if (indice < (panelDescubrirFrase.getChildCount()/3))
                return escribirPalabra(palabraActual, palabraActualSolucion,(panelDescubrirFrase.getChildCount()/3));
            else if (indice < 2*(panelDescubrirFrase.getChildCount()/3))
                return escribirPalabra(palabraActual, palabraActualSolucion,2*(panelDescubrirFrase.getChildCount()/3));
            else
                return -1;
        }

        for (int i = 0; i < palabraActual.length; i++) {
            Button boton = (Button) panelDescubrirFrase.getChildAt(indice + i);
            DFButton botonSolucion = new DFButton(boton, palabraActualSolucion[i]);
            botonYSolucion.put(boton, botonSolucion.getLetra());

            boton.setText(palabraActual[i] + "");
            if ((palabraActual[i] + "").matches("[A-Z]*") ||
                    (palabraActual[i] + "").matches("[a-z]*"))
                boton.setBackgroundColor(Color.LTGRAY);
            else if (palabraActual[i] == '_') {
                boton.setBackground(getDrawable(R.drawable.boton_panel));
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ultimaAcertada) casillaElegida.setBackgroundColor(Color.WHITE);
                        ultimaAcertada = false;
                        casillaElegida = boton;
                        casillaElegidaSolucion = botonSolucion;
                        casillaElegida.setBackgroundColor(Color.LTGRAY);
                    }
                });
            }
            else {
                boton.setBackground(getDrawable(R.drawable.botones_teclado));
                boton.setClickable(false);
            }
        }
        indice += palabraActual.length;
        return indice;
    }

    private void startTimer(int t) {
        int tiempo = t;
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
        MainActivity.music.setLooping(true);
        MainActivity.music.start();
        timeCount = tiempo;
        timeBar = findViewById(R.id.timeBar3);
        timeBar.bringToFront();
        timeBar.setMax(tiempo);
        timeBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        timeBar.setProgress(tiempo);
        mCountDownTimer = new CountDownTimer(tiempo, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeCount -= 100;
                if(timeCount == 10000) {
                    MainActivity.music.stop();
                    MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.finalcountdown);
                    MainActivity.music.start();
                }
                if (timeCount == tiempo / 3) {
                    timeBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
                if (timeCount == tiempo * 2 / 3) {
                    timeBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                }
                timeBar.setProgress(timeCount);
            }

            @Override
            public void onFinish() {
                timeCount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        MainActivity.music.stop();

                        if (tiempo == Tiempo) {
                            terminoMal();
                        } else if (tiempo == TiempoOpcion) {
                            if (!Acierto) {
                                setResult(RESULT_CANCELED); finish();
                            } else {
                                setResult(RESULT_OK); finish();
                            }
                        }
                    }
                });
            }
        };
        mCountDownTimer.start();
    }

    public void terminoBien(){
            MainActivity.background.pause();

            cantidadRetosContestados++;
            PuntosTotales += 100 * Nivel;

            visualizacionBotonConsolidar(true);
            acierto_fallo.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));

            if (haConsolidado) {
                botonSiguientePregunta.setText("CONTINUAR");
            } else {
                botonSiguientePregunta.setText("CONTINUAR SIN CONSOLIDAR");
            }

            if (cantidadRetosContestados > 10) {
                imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));
                textoPuntosFinales.setText("Tu puntuacion final es de: " + PuntosTotales);
                MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.winner);
                MainActivity.music.start();
                pantalla_final();
                return;
            } else {
                pantallaAciertoFallo();
            }

            startTimer(TiempoOpcion);
    }
    public void terminoMal() {
            MainActivity.background.pause();
            Vidas--;
            if (Vidas < 0) {
                PuntosTotales = 0;
                PuntosConsolidados = 0;
                imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
                textoPuntosFinales.setText("Puntos totales: 0");
                MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.loser);
                MainActivity.music.start();
                pantalla_final();
                return;
            }
            startTimer(TiempoOpcion);

            PuntosTotales -= 100 * Nivel * 2;
            if (PuntosTotales < 0) PuntosTotales = 0;
            textoPuntosGanados.setText(100 * Nivel * (-2) + " puntos perdidos ");
            botonSiguientePregunta.setText("CONTINUAR");
            textoPuntosTotal.setText("Puntos Totales: " + PuntosTotales);
            visualizacionBotonConsolidar(false);

            acierto_fallo.setImageDrawable(getDrawable(R.drawable.vuelve_intentar));
            pantallaAciertoFallo();
    }

    public void pantalla_final() {
        pantalla_final.setVisibility(View.VISIBLE);
        botonTerminar.bringToFront();
        timeBar.setVisibility(View.INVISIBLE);
    }
    public void visualizacionBotonConsolidar(Boolean respuestaCorrecta) {
        System.out.println("ha consolidado: " + haConsolidado);
        if (respuestaCorrecta && !haConsolidado) {
            botonConsolidar.setVisibility(View.VISIBLE);
        } else {
            botonConsolidar.setVisibility(View.INVISIBLE);
        }
    }
    public void pantallaAciertoFallo() {
        textoPuntosTotal.setText("PUNTOS TOTAL = " + PuntosTotales);
        contenedor.setVisibility(View.VISIBLE);
    }

    public void continuarOnClick(View v) {
        mCountDownTimer.cancel();
        MainActivity.music.stop();
        Intent t = new Intent(getApplicationContext(), MediadorDeRetos.class);
        t.putExtra("Acierto", Acierto);

        if (!Acierto) {
            setResult(RESULT_CANCELED);
        } else {
            if (Consolidado) setResult(RESULT_FIRST_USER);
            else setResult(RESULT_OK);
        }

        finish();
    }

    public void consolidarYContinuar(View v) {
        PuntosConsolidados = Nivel*100;
        Consolidado = true;
        continuarOnClick(v);
    }
    public void SavePoints(int Points) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                MainActivity.user.setPointsAchieved(MainActivity.user.getPointsAchieved() + Points);
                u.actualizar(MainActivity.user);
            }
        }).start();
    }

    public void guardarPuntuacion() {
        MainActivity.music.stop();
        if (cantidadRetosContestados > 10) {
            SavePoints(PuntosTotales);
        } else {
            SavePoints(PuntosConsolidados);
        }
        mCountDownTimer.cancel();

    }
    public void clickBotonTerminar(View v) {
        guardarPuntuacion();
        Intent t = new Intent();
        t.putExtra("Acierto", Acierto);

        if (Abandono) setResult(ABANDON);
        else if (Acierto) setResult(RESULT_OK);
        else setResult(RESULT_CANCELED);
        finish();
    }

    public void abandonarClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estas seguro que quieres abandonar?")
                .setMessage("Obtendras los puntos consolidados")
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCountDownTimer.cancel();
                        MainActivity.music.stop();
                        imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.no_esta_mal));
                        textoPuntosFinales.setText("Tu puntuacion final es de: " + PuntosConsolidados);
                        Abandono = true;
                        pantalla_final();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void pistaOnClick(View v) {
        if (MediadorDeRetos.pistas == 0) {
            imagenPista.setEnabled(false);
            imagenPista.setOnClickListener(null);
            return;
        }

        imagenPista.setOnClickListener(null);
        imagenPista.setImageDrawable(getDrawable(R.drawable.pista2));
        Random letraAleatoria = new Random();
        char letraRandom = listadoCaracteresFrase.get(letraAleatoria.nextInt(listadoCaracteresFrase.size()));
        letraRandom = Character.toUpperCase(letraRandom);

        if (!listaLetrasMostradasPista.contains(letraRandom)) listaLetrasMostradasPista.add(letraRandom);
        else {
            pistaOnClick(v);
            return;
        }

        MediadorDeRetos.pistas--;
        contadorPistas.setText(MediadorDeRetos.pistas + " / 3");
        textoPuntuacionPregunta.setText("Por " + Nivel*50 + " puntos.");

        Animation animacionAparecer = new AlphaAnimation(0.0f, 1.0f);
        animacionAparecer.setDuration(150);
        Animation animacionDesaparecer = new AlphaAnimation(1.0f, 0.0f);
        animacionDesaparecer.setDuration(150);

        textoMostrarPista.setText("Mostrando: " + letraRandom);
        contenedorMostrarPista.setVisibility(View.VISIBLE);
        contenedorMostrarPista.startAnimation(animacionAparecer);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                contenedorMostrarPista.setVisibility(View.INVISIBLE);
                contenedorMostrarPista.startAnimation(animacionDesaparecer);
            }
        }, 1000);

        destaparCasillas(letraRandom);
    }

    public void destaparCasillas(char letra) {
        for (int i = 0; i < panelDescubrirFrase.getChildCount(); i++) {
            if (letra == Character.toUpperCase(botonYSolucion.getOrDefault((Button) panelDescubrirFrase.getChildAt(i), '*'))) {
                ((Button) panelDescubrirFrase.getChildAt(i)).setText(letra+"");
                ((Button) panelDescubrirFrase.getChildAt(i)).setBackgroundColor(Color.LTGRAY);
                ((Button) panelDescubrirFrase.getChildAt(i)).setOnClickListener(null);
            }
        }
        List<Character> nuevoListado = new ArrayList<>();
        for (int i = 0; i < letrasLayout.getChildCount(); i++) {
            if (!(letra+"").toLowerCase().equals(((Button) letrasLayout.getChildAt(i)).getText()))
                nuevoListado.add(((Button) letrasLayout.getChildAt(i)).getText().charAt(0));
        }
        letrasLayout.removeAllViews();
        rellenarLetrasLayout(nuevoListado);
    }

    @Override
    public void onBackPressed() { }
}