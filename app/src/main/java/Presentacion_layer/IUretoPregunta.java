package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.sql.SQLException;
import java.util.List;

import Builder.BuilderPartidaRetoPregunta;
import Builder.CreadorDePartida;
import Domain_Layer.Answer;
import Domain_Layer.PartidaRetoPregunta;
import Domain_Layer.Question;
import Persistence.AnswerRepository;
import Persistence.QuestionRepository;
import Persistence.UserRepository;

public class IUretoPregunta extends AppCompatActivity {
    RelativeLayout contenedor;
    RelativeLayout pantalla_final;
    ConstraintLayout contenedor_principal;
    Button botonRespuesta1;
    ImageView ods;
    ImageView acierto_fallo;
    ImageView imagenCorazon;
    Button botonRespuesta2;
    Button botonRespuesta3;
    Button botonRespuesta4;
    TextView textoPuntosTotal;

    TextView textPuntosAcumulados;

    TextView textPuntosConsolidados;
    Button botonConsolidar;
    Button botonSiguientePregunta;
    TextView textoPuntosGanados, textoContadorDePreguntas;
    QuestionRepository preguntasEnBD;
    List<Answer> respuestasActuales;
    int indicePreguntasFacil = 0, indicePreguntasMedio = 0, indicePreguntasDificil = 0;
    List<Answer> listaRespuestas;
    ProgressBar timeBar;
    ImageView abandonar;
    CountDownTimer mCountDownTimer;
    //PartidaRetoPregunta juego;
    TextView textoPregunta = null;
    ImageView imagenPantallaFinal;
    TextView textoPuntosFinales;
    int respuestasCorrectasContestadas = 1;
    int vida = 1;

    int timeCount = 0;
    int puntosTotales = 0;
    int puntosConsolidados;
    boolean haConsolidado = false;

    boolean Consolidado;
    int QuestionID;

    int Tiempo;

    int TiempoOpcion;

    int Nivel;

    Question preguntaActual;

    boolean Acierto;

    int SonidoFallo;

    int SonidoAcierto;

    public IUretoPregunta() throws SQLException {
    }
    //  JuegoBuilder retoPegunta = new JuegoRetoPregunta();
    //CreadorDeJuego creadorDeJuego = new CreadorDeJuego();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textPuntosConsolidados = findViewById(R.id.textPuntosConsolidados);
        textPuntosAcumulados = findViewById(R.id.textPuntosAcumulados);
        textoPregunta = findViewById(R.id.textoPregunta);
        textoPuntosGanados = findViewById(R.id.puntosGanados);
        contenedor_principal = findViewById(R.id.layout);
        contenedor = findViewById(R.id.contenedor_resp);
        pantalla_final = findViewById(R.id.contenedor_final);
        acierto_fallo = findViewById(R.id.imagen_acierto);
        textoPuntosTotal = findViewById(R.id.puntosTotal);
        botonRespuesta1 = findViewById(R.id.botonRespuesta1);
        botonRespuesta2 = findViewById(R.id.botonRespuesta2);
        botonRespuesta3 = findViewById(R.id.botonRespuesta3);
        botonRespuesta4 = findViewById(R.id.botonRespuesta4);
        botonConsolidar = findViewById(R.id.botonConsolidar);
        ods = findViewById(R.id.imagen_ods);
        imagenCorazon = findViewById(R.id.imagenCorazon);
        botonSiguientePregunta = findViewById(R.id.botonSiguientePregunta);
        textoContadorDePreguntas = findViewById(R.id.textoContadorDePreguntas);
        contenedor = findViewById(R.id.contenedor_resp);
        abandonar = findViewById(R.id.abandonar);
        imagenPantallaFinal = findViewById(R.id.imagenPantallaFinal);
        textoPuntosFinales = findViewById(R.id.textoPuntosFinales);

        CargarDatos();
        try {
            IniciarBaseDedatos();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ponerTextoEnPantalla();
        startTimer(Tiempo);
    }

    private void IniciarBaseDedatos() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    listaRespuestas = new AnswerRepository(MainActivity.conexion).obtenerTodos();
                    preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    preguntaActual = preguntasEnBD.obtener(QuestionID);
                    respuestasActuales = preguntasEnBD.getRespuestasPregunta(preguntaActual);

                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        });
        t.start();
        t.join();


    }

    public void CargarDatos() {
        Bundle extras = getIntent().getExtras();

        QuestionID = extras.getInt("idPregunta");
        puntosTotales = extras.getInt("PuntosTotales");
        puntosConsolidados = extras.getInt("PuntosConsolidados");
        vida = extras.getInt("Vidas");
        respuestasCorrectasContestadas = extras.getInt("Ronda");
        Consolidado = extras.getBoolean("haConsolidado");
        Tiempo = extras.getInt("Tiempo");
        TiempoOpcion = extras.getInt("TiempoOpcion");
        Nivel = extras.getInt("Nivel");
        SonidoAcierto = extras.getInt("SonidoAcierto");
        SonidoFallo = extras.getInt("SonidoFallo");

        if(vida == 0) {
            imagenCorazon.setImageDrawable(getDrawable(R.drawable.corazon_roto));
        }

        if(!Consolidado) {
            abandonar.setVisibility(View.INVISIBLE);
        }
        if(Nivel == 2) {
            contenedor_principal.setBackground(getDrawable(R.drawable.fondo_nivel_medio));
        }

        else if(Nivel == 3)
        {
            contenedor_principal.setBackground(getDrawable(R.drawable.fondo_nivel_dificil));
        }
        textoContadorDePreguntas.setText(respuestasCorrectasContestadas + "/10");
        textPuntosAcumulados.setText("Puntos Totales: " + puntosTotales);
        textPuntosConsolidados.setText("Puntos Consolidados: " + puntosConsolidados);
    }
    private void ponerTextoEnPantalla() {

        tamañoOriginal();
        textoPregunta.setText(preguntaActual.getStatement());
        botonRespuesta1.setText(respuestasActuales.get(0).getText());
        botonRespuesta2.setText(respuestasActuales.get(1).getText());
        botonRespuesta3.setText(respuestasActuales.get(2).getText());
        botonRespuesta4.setText(respuestasActuales.get(3).getText());
        longitudRespuesta();
        longitudTexto();
        poner_imagen_ods();

    }

    private void longitudRespuesta() {
        Button[] botones = {botonRespuesta1, botonRespuesta2, botonRespuesta3, botonRespuesta4};
        for (Button b : botones) {
            if (b.length() > 70) {
                b.setTextSize(12);
                if (b.length() > 90) {
                    b.setTextSize(9);
                }
            }
        }
    }

    private void longitudTexto() {
        if (textoPregunta.length() > 130) {
            textoPregunta.setTextSize(16);
        }
    }

    public void tamañoOriginal() {
        botonRespuesta1.setTextSize(14);
        botonRespuesta2.setTextSize(14);
        botonRespuesta3.setTextSize(14);
        botonRespuesta4.setTextSize(14);
        textoPregunta.setTextSize(20);
    }

    public void poner_imagen_ods() {
        int numeroOds = preguntaActual.getOds();
        int imagenId = getResources().getIdentifier("ods_" + numeroOds, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(imagenId);
        ods.setImageDrawable(imagen);
    }

    private void startTimer(int t) {
        int tiempo = t;
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
        MainActivity.music.start();
        timeCount = tiempo;
        timeBar = findViewById(R.id.timeBar);
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
                            wrongAnswer(2, -1);
                        } else if (tiempo == TiempoOpcion) {
                            puntosTotales = 0;
                            puntosConsolidados = 0;
                            imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
                            textoPuntosFinales.setText("Puntos totales: 0");
                            quitarPantallaAciertoFallo();
                            botonSiguientePregunta.performClick();
                        }
                    }
                });
            }
        };
        mCountDownTimer.start();
    }

    public int botonSeleccionado() {
        int indice = 0;
        if (botonRespuesta1.isPressed()) {
            indice = 0;
        } else if (botonRespuesta2.isPressed()) {
            indice = 1;
        } else if (botonRespuesta3.isPressed()) {
            indice = 2;
        } else if (botonRespuesta4.isPressed()) {
            indice = 3;
        }
        return indice;
    }

    public void metodoBotonSiguiente(View v) throws SQLException {
        mCountDownTimer.cancel();
        MainActivity.music.stop();
        Intent t = new Intent();
        t.putExtra("Acierto", Acierto);
        if(haConsolidado) {
            setResult(RESULT_FIRST_USER);
        }
        else if (Acierto) {setResult(RESULT_OK);}
        else {setResult(RESULT_CANCELED);}

        finish();
    }

    public void onClick(View view) {
        mCountDownTimer.cancel();
        int indiceProvisional = botonSeleccionado();

        if (respuestasActuales.get(indiceProvisional).isCorrect()) {
            correctAnswer(100 * Nivel, indiceProvisional);
        } else {
            wrongAnswer(100 * Nivel * (-2), indiceProvisional);
        }
    }

    public void pantallaAciertoFallo() {
        contenedor.setVisibility(View.VISIBLE);
        botonRespuesta1.setClickable(false);
        botonRespuesta2.setClickable(false);
        botonRespuesta3.setClickable(false);
        botonRespuesta4.setClickable(false);
    }


    public void quitarPantallaAciertoFallo() {
        contenedor.setVisibility(View.GONE);
        botonRespuesta1.setClickable(true);
        botonRespuesta2.setClickable(true);
        botonRespuesta3.setClickable(true);
        botonRespuesta4.setClickable(true);
        botonRespuesta1.setBackground(getDrawable(R.drawable.boton_azul));
        botonRespuesta2.setBackground(getDrawable(R.drawable.boton_azul));
        botonRespuesta3.setBackground(getDrawable(R.drawable.boton_azul));
        botonRespuesta4.setBackground(getDrawable(R.drawable.boton_azul));
    }

    public void correctAnswer(int screenText, int index) {
        Acierto = true;
        MainActivity.music.stop();
        MainActivity.music = MediaPlayer.create(getApplicationContext(), SonidoAcierto);
        MainActivity.music.start();


        respuestasCorrectasContestadas++;
        puntosTotales += 100 * Nivel;
        textoPuntosGanados.setText("+" + screenText + " puntos ganados!");
        textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);

        cambiarColorAVerde(index);
        visualizacionBotonConsolidar(true);
        acierto_fallo.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));

        if (Consolidado) {
            botonSiguientePregunta.setText("CONTINUAR");
        } else {
            botonSiguientePregunta.setText("CONTINUAR SIN CONSOLIDAR");
        }

        if (respuestasCorrectasContestadas > 10) {
            imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));
            textoPuntosFinales.setText("Tu puntuacion final es de: " + puntosTotales);
            MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.winner);
            MainActivity.music.start();
            pantalla_final();
            return;
        } else {

            pantallaAciertoFallo();
        }

        startTimer(TiempoOpcion);

    }

    public int getCorrectOption() {
        for(int i = 0; i <= respuestasActuales.size(); i++) {
            if(respuestasActuales.get(i).isCorrect()) {return i;}
        }
        return -1;
    }

    public void wrongAnswer(int screenText, int index) {
        MainActivity.music.stop();
        MainActivity.music = MediaPlayer.create(getApplicationContext(), SonidoFallo);
        MainActivity.music.start();
        vida--;
        if(getCorrectOption() != -1) {cambiarColorAVerde(getCorrectOption());}
        if (index != -1) cambiarColorARojo(index);
        if (vida < 0) {
            puntosTotales = 0;
            puntosConsolidados = 0;
            imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
            textoPuntosFinales.setText("Puntos totales: 0");
            MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.loser);
            MainActivity.music.start();
            pantalla_final();
            return;
        }
        startTimer(TiempoOpcion);

        puntosTotales -= 100 * Nivel * 2;
        if (puntosTotales < 0) puntosTotales = 0;
        textoPuntosGanados.setText(screenText + " puntos perdidos ");
        if (screenText == 2) {
            textoPuntosGanados.setText("Se acabo el tiempo");
        }
        botonSiguientePregunta.setText("CONTINUAR");
        textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);
        visualizacionBotonConsolidar(false);

        acierto_fallo.setImageDrawable(getDrawable(R.drawable.vuelve_intentar));
        pantallaAciertoFallo();
    }

    public void visualizacionBotonConsolidar(Boolean respuestaCorrecta) {

        if (respuestaCorrecta && !Consolidado) {
            botonConsolidar.setVisibility(View.VISIBLE);
        } else {
            botonConsolidar.setVisibility(View.INVISIBLE);
        }

    }

    public void SavePoints(int Points) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(MainActivity.conexion);
                MainActivity.user.setPointsAchieved(MainActivity.user.getPointsAchieved() + Points);
                u.actualizar(MainActivity.user);
            }
        }).start();
    }

    public void clickBotonConsolidar(View v) {
        haConsolidado = true;
        botonSiguientePregunta.performClick();
    }

    public void cambiarColorARojo(int botonEscogido) {

        if (botonEscogido == 0) {
            botonRespuesta1.setBackground(getDrawable(R.drawable.boton_rojo));
        } else if (botonEscogido == 1) {
            botonRespuesta2.setBackground(getDrawable(R.drawable.boton_rojo));
        } else if (botonEscogido == 2) {
            botonRespuesta3.setBackground(getDrawable(R.drawable.boton_rojo));
        } else if (botonEscogido == 3) {
            botonRespuesta4.setBackground(getDrawable(R.drawable.boton_rojo));
        }

    }

    public void cambiarColorAVerde(int botonEscogido) {

        if (botonEscogido == 0) {
            botonRespuesta1.setBackground(getDrawable(R.drawable.boton_verde));
        } else if (botonEscogido == 1) {
            botonRespuesta2.setBackground(getDrawable(R.drawable.boton_verde));
        } else if (botonEscogido == 2) {
            botonRespuesta3.setBackground(getDrawable(R.drawable.boton_verde));
        } else if (botonEscogido == 3) {
            botonRespuesta4.setBackground(getDrawable(R.drawable.boton_verde));
        }

    }
    public void guardarPuntuacion() {
        MainActivity.music.stop();
        if (respuestasCorrectasContestadas > 10) {
            SavePoints(puntosTotales);
        } else {
            SavePoints(puntosConsolidados);
        }
        mCountDownTimer.cancel();

    }

    public void pantalla_final() {
        pantalla_final.setVisibility(View.VISIBLE);
        botonRespuesta1.setClickable(false);
        botonRespuesta2.setClickable(false);
        botonRespuesta3.setClickable(false);
        botonRespuesta4.setClickable(false);
        timeBar.setVisibility(View.INVISIBLE);

    }

    public void abandonOnClick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estas seguro que quieres abandonar? Obtendras los puntos consolidados")
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCountDownTimer.cancel();
                        MainActivity.music.stop();
                        imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.no_esta_mal));
                        textoPuntosFinales.setText("Tu puntuacion final es de: " + puntosConsolidados);
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

    public void clickBotonTerminarPartida(View v) {
        guardarPuntuacion();
        Intent intent = new Intent(IUretoPregunta.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
