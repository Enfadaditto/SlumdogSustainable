package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import Domain_Layer.DFButton;
import Domain_Layer.Frase;

public class IUretoFrase extends AppCompatActivity {
    LinearLayout letrasLayout;
    RelativeLayout panelDescubrirFrase;
    Frase frase;
    char[] fraseProblema;
    List<Character> listadoCaracteresFrase;

    //MODIFICAR ESTO
    Button casillaElegida;
    DFButton casillaElegidaSolucion;
    TextView descripcionFrase;
    boolean ultimaAcertada;

    String fraseEnunciado;
    String descripcionEnunciado;
    int PuntosTotales, PuntosConsolidados, Vidas, cantidadRetosContestados;
    boolean haConsolidado;
    int TiempoOpcion, Tiempo, Nivel, SonidoFallo, SonidoAcierto;
    int timeCount = 0;
    ProgressBar timeBar;
    CountDownTimer mCountDownTimer;
    RelativeLayout pantalla_final;
    ImageView imagenPantallaFinal;
    TextView textoPuntosFinales, textoPuntosGanados, textoPuntosTotal;
    Button botonConsolidar, botonSiguientePregunta;
    ImageView acierto_fallo;
    RelativeLayout contenedor;
    boolean Acierto;


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

        fraseProblema = frase.getFraseProblema(Nivel);
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);
        casillaElegida = new DFButton(this);

        rellenarLetrasLayout(listadoCaracteresFrase);
        letrasLayout.setVisibility(View.INVISIBLE);

        descripcionFrase.setText("Haz click AQUI para comenzar");
        descripcionFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComenzarReto();
                letrasLayout.setVisibility(View.VISIBLE);
                descripcionFrase.setText(descripcionEnunciado);
                descripcionFrase.setOnClickListener(null);

                startTimer(Tiempo);
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

        frase.setFrase(fraseEnunciado);
        //poner_imagen_ods();
    }


    private void ComenzarReto() {
        for (int i = 0; i < panelDescubrirFrase.getChildCount(); i++) {
            ((Button) panelDescubrirFrase.getChildAt(i)).setText("");
            ((Button) panelDescubrirFrase.getChildAt(i)).setBackground(getDrawable(R.drawable.boton_panel_vacio));
        }
        ponerFrasePorPantalla();
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
                            //Se acabo el tiempo, marcar como mala
                            terminoMal(1);
                        } else if (tiempo == TiempoOpcion) {
                           /* puntosTotales = 0;
                            puntosConsolidados = 0;
                            imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
                            textoPuntosFinales.setText("Puntos totales: 0");
                            quitarPantallaAciertoFallo();
                            botonSiguientePregunta.performClick();

                            */
                        }
                    }
                });
            }
        };
        mCountDownTimer.start();
    }

    public void terminoBien(){
            MainActivity.background.pause();
            Acierto = true;

            cantidadRetosContestados++;
            PuntosTotales += 100 * Nivel;
            // textoPuntosGanados.setText("+" + screenText + " puntos ganados!");
            // textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);


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
    public void terminoMal(int tipo) {
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
            textoPuntosGanados.setText(100 * Nivel + (-2) + " puntos perdidos ");
            if (tipo == 2) {
                textoPuntosGanados.setText("Se acabo el tiempo");
            }
            botonSiguientePregunta.setText("CONTINUAR");
            textoPuntosTotal.setText("Puntos Totales: " + PuntosTotales);
            visualizacionBotonConsolidar(false);

            acierto_fallo.setImageDrawable(getDrawable(R.drawable.vuelve_intentar));
            pantallaAciertoFallo();
    }

    public void pantalla_final() {
        pantalla_final.setVisibility(View.VISIBLE);
        timeBar.setVisibility(View.INVISIBLE);
    }
    public void visualizacionBotonConsolidar(Boolean respuestaCorrecta) {
        if (respuestaCorrecta && !haConsolidado) {
            botonConsolidar.setVisibility(View.VISIBLE);
        } else {
            botonConsolidar.setVisibility(View.INVISIBLE);
        }
    }
    public void pantallaAciertoFallo() {
        contenedor.setVisibility(View.VISIBLE);
    }
}