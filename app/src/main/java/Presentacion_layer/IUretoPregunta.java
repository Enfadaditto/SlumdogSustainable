package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Builder.CreadorDeJuego;
import Builder.Juego;
import Builder.JuegoBuilder;
import Builder.JuegoRetoPregunta;
import Domain_Layer.Answer;
import Domain_Layer.Question;
import Persistence.AnswerRepository;
import Persistence.QuestionRepository;
import Persistence.UserRepository;

public class IUretoPregunta extends AppCompatActivity {

    private static final String DIFICULTAD_FACIL = "Baja";
    private static final String DIFICULTAD_MEDIA = "Media";
    private static final String DIFICULTAD_DIFICIL = "Alta";

    ImageView fondo_transparente;
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
    Question preguntaActual = new Question();
    List<Answer> respuestasActuales;
    int indicePreguntasFacil = 0, indicePreguntasMedio = 0, indicePreguntasDificil = 0;
    List<Answer> listaRespuestas;
    ProgressBar timeBar;
    ImageView abandonar;
    CountDownTimer mCountDownTimer;
    List<Question> listaPreguntasDifultad1, listaPreguntasDifultad2, listaPreguntasDifultad3;
    Thread timeBarThread;
    Juego juego;
    TextView textoPregunta = null;
    ImageView imagenPantallaFinal;
    TextView textoPuntosFinales;
    int respuestasCorrectasContestadas = 1;
    int vida = 1;

    int timeCount = 0;
    boolean respuestaEscogida;
    int puntosTotales = 0;
    int nivel = 1;
    int puntosConsolidados;
    boolean haConsolidado = false;
    int indiceDeListaParaPregunta;

    public IUretoPregunta() throws SQLException {
    }

    //  JuegoBuilder retoPegunta = new JuegoRetoPregunta();
    //CreadorDeJuego creadorDeJuego = new CreadorDeJuego();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);

        IniciarBaseDedatos();

        textPuntosConsolidados = findViewById(R.id.textPuntosConsolidados);
        textPuntosAcumulados = findViewById(R.id.textPuntosAcumulados);
        textoPregunta = findViewById(R.id.textoPregunta);
        textoPuntosGanados = findViewById(R.id.puntosGanados);
        contenedor_principal = findViewById(R.id.layout);
        contenedor = findViewById(R.id.contenedor_resp);
        pantalla_final = findViewById(R.id.contenedor_final);
        acierto_fallo = findViewById(R.id.imagen_acierto);
        //fondo_transparente = findViewById(R.id.fondo_t);
        textoPuntosTotal = findViewById(R.id.puntosTotal);
        botonRespuesta1 = (Button) findViewById(R.id.botonRespuesta1);
        botonRespuesta2 = (Button) findViewById(R.id.botonRespuesta2);
        botonRespuesta3 = (Button) findViewById(R.id.botonRespuesta3);
        botonRespuesta4 = (Button) findViewById(R.id.botonRespuesta4);
        botonConsolidar = (Button) findViewById(R.id.botonConsolidar);
        ods = findViewById(R.id.imagen_ods);
        imagenCorazon = findViewById(R.id.imagenCorazon);
        botonSiguientePregunta = (Button) findViewById(R.id.botonSiguientePregunta);
        textoContadorDePreguntas = findViewById(R.id.textoContadorDePreguntas);
        contenedor = findViewById(R.id.contenedor_resp);
        abandonar = findViewById(R.id.abandonar);
        imagenPantallaFinal = findViewById(R.id.imagenPantallaFinal);
        textoPuntosFinales = findViewById(R.id.textoPuntosFinales);
        //-------builder--------//
        JuegoBuilder retoPegunta = new JuegoRetoPregunta();
        CreadorDeJuego creadorDeJuego = new CreadorDeJuego();
        creadorDeJuego.setJuegoBuilder(retoPegunta);
        creadorDeJuego.construirJuego();
        juego = retoPegunta.getJuego();

        textPuntosConsolidados.setVisibility(View.INVISIBLE);
        abandonar.setVisibility(View.INVISIBLE);
        textPuntosAcumulados.setVisibility(View.INVISIBLE);
        startTimer();
    }

    private void IniciarBaseDedatos() {
        new Thread(new Runnable() {
            public void run() {
                try {


                    preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    listaRespuestas = new AnswerRepository(MainActivity.conexion).obtenerTodos();
                    listaPreguntasDifultad1 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_FACIL);
                    listaPreguntasDifultad2 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_MEDIA);
                    listaPreguntasDifultad3 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_DIFICIL);
                    Collections.shuffle(listaPreguntasDifultad1, new Random());
                    Collections.shuffle(listaPreguntasDifultad2, new Random());
                    Collections.shuffle(listaPreguntasDifultad3, new Random());
                    preguntaActual = listaPreguntasDifultad1.get(indicePreguntasFacil++);
                    respuestasActuales = getRespuestasPregunta(preguntaActual);
                    respuestasActuales = preguntasEnBD.getAnswers(preguntaActual);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            ponerTextoEnPantalla();
                        }
                    });


                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        }).start();


    }

    private void ponerTextoEnPantalla() {
        int indice = 0;
        tamañoOriginal();
        textoPregunta.setText(preguntaActual.getStatement());

        botonRespuesta1.setText(respuestasActuales.get(0).getText());
        botonRespuesta2.setText(respuestasActuales.get(1).getText());
        botonRespuesta3.setText(respuestasActuales.get(2).getText());
        botonRespuesta4.setText(respuestasActuales.get(3).getText());
        longitudRespuesta();
        poner_imagen_ods();

    }
    private void longitudRespuesta(){
        Button[] botones = {botonRespuesta1, botonRespuesta2, botonRespuesta3, botonRespuesta4};
        for(Button b : botones){
            if(b.length() > 70){
                b.setTextSize(12);
                if(b.length() > 90){
                    b.setTextSize(9);
                }
            }
        }
    }
    public void tamañoOriginal(){
        botonRespuesta1.setTextSize(14);
        botonRespuesta2.setTextSize(14);
        botonRespuesta3.setTextSize(14);
        botonRespuesta4.setTextSize(14);
    }

    public void poner_imagen_ods() {
        int numeroOds = preguntaActual.getOds();
        int imagenId = getResources().getIdentifier("ods_" + numeroOds, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(imagenId);
        ods.setImageDrawable(imagen);
    }

    private void startTimer() {
        int tiempo =juego.getTiempo();
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
        MainActivity.music.start();
        timeBar = findViewById(R.id.timeBar);
        timeBar.setMax(tiempo);
        //timeBar.setProgress(tiempo);
        timeBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        /*
        timeBarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < tiempo; i++) {
                        try {
                            wait(1);
                        } catch (InterruptedException e) {
                            return;
                        }
                        if (i == tiempo / 3) {
                            timeBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                        }
                        if (i == tiempo * 2 / 3) {
                            timeBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        }
                        timeBar.incrementProgressBy(-1);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            wrongAnswer(2, -1);
                        }
                    });
                }
            }
        });
        timeBarThread.start();
        */

        timeCount = tiempo;
        timeBar.setProgress(tiempo);
        mCountDownTimer=new CountDownTimer(tiempo,100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeCount-=100;
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
                //Do what you want
                timeCount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wrongAnswer(2, -1);
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
        if (respuestasCorrectasContestadas <= 4) {
            preguntaActual = listaPreguntasDifultad1.get(indicePreguntasFacil++);
        } else if (respuestasCorrectasContestadas > 4 && respuestasCorrectasContestadas <= 7) {
            nivel = 2;
            preguntaActual = listaPreguntasDifultad2.get(indicePreguntasMedio++);
            contenedor_principal.setBackground(getDrawable(R.drawable.fondo_nivel_medio));
        } else if (respuestasCorrectasContestadas == 10){
            guardarPuntuacion();
            return;
        }
        else {
            nivel = 3;
            preguntaActual = listaPreguntasDifultad3.get(indicePreguntasDificil++);
            contenedor_principal.setBackground(getDrawable(R.drawable.fondo_nivel_dificil));
        }
        textPuntosAcumulados.setVisibility(View.VISIBLE);
        respuestasActuales = getRespuestasPregunta(preguntaActual);
        ponerTextoEnPantalla();
        quitarPantallaAciertoFallo();
        poner_imagen_ods();
        startTimer();
    }

    public void onClick(View view) {
        mCountDownTimer.cancel();
        //this.timeBarThread.interrupt();
        int indiceProvisional = botonSeleccionado();

        if (respuestasActuales.get(indiceProvisional).isCorrect()) {
            correctAnswer(juego.getPuntos() * nivel, indiceProvisional);
        } else {
            wrongAnswer(juego.getPuntos() * nivel * (-2), indiceProvisional);
        }
        textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);
        textPuntosAcumulados.setText("Puntos Totales: " + puntosTotales);
    }

    public void pantallaAciertoFallo() {
        contenedor.setVisibility(View.VISIBLE);
        botonRespuesta1.setClickable(false);
        botonRespuesta2.setClickable(false);
        botonRespuesta3.setClickable(false);
        botonRespuesta4.setClickable(false);
    }

    public List<Answer> getRespuestasPregunta(Question q) {
        List<Answer> resultlist = new ArrayList<>();
        for (Answer a : listaRespuestas) {
            if (a.getQuestionID() == q.getQuestionID()) {
                resultlist.add(a);
            }
        }
        return resultlist;
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
        MainActivity.music.stop();
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.correctanswer);
        MainActivity.music.start();
        respuestasCorrectasContestadas++;
        puntosTotales += juego.getPuntos() * nivel;
        textoPuntosGanados.setText("+"+screenText + " puntos ganados!");
        textoPuntosTotal.setText("Puntos Totales = " + puntosTotales);
        textPuntosAcumulados.setText("Puntos Totales: " + puntosTotales);

        cambiarColorAVerde(index);
        incrementarTextoCantidadDeContestadas();
        visualizacionBotonConsolidar(true);
        acierto_fallo.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));

        pantallaAciertoFallo();
    }

    public void wrongAnswer(int screenText, int index) {
        MainActivity.music.stop();
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.wronganswer);
        MainActivity.music.start();
        vida--;
        if (vida < 0) {
            puntosTotales = 0;
            //puntosConsolidados = 0;
            imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
            textoPuntosFinales.setText("Puntos totales: 0");
            pantalla_final();
           // Intent intent = new Intent(IUretoPregunta.this, MainActivity.class); //Creo que esto tiene que llevar una pantalla de GAMEOVER
           // startActivity(intent);
           // finish();

            //return;
        }

        if(haConsolidado && vida == 0 ){
            puntosConsolidados -= nivel* juego.getPuntos()*2;
            textPuntosConsolidados.setText("Puntos consolidados: "+ puntosConsolidados);

        }

        puntosTotales -= juego.getPuntos() * nivel * 2;
        if (puntosTotales < 0) puntosTotales = 0;
        textoPuntosGanados.setText(screenText + " puntos perdidos ");
        if (screenText == 2) {
            textoPuntosGanados.setText("Se acabo el tiempo");
        }
        if (index != -1) cambiarColorARojo(index);

        textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);
        textPuntosAcumulados.setText("Puntos Totales: " + puntosTotales);
        visualizacionBotonConsolidar(false);

        imagenCorazon.setImageDrawable(getDrawable(R.drawable.corazon_roto));
        acierto_fallo.setImageDrawable(getDrawable(R.drawable.vuelve_intentar));
        pantallaAciertoFallo();
    }

    public void visualizacionBotonConsolidar(Boolean respuestaCorrecta) {

        if (respuestaCorrecta && !haConsolidado) {
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

        textPuntosConsolidados.setVisibility(View.VISIBLE);
        abandonar.setVisibility(View.VISIBLE);
        haConsolidado = true;
        botonConsolidar.setBackground(getDrawable(R.drawable.boton_verde));
        puntosConsolidados = puntosTotales;
        textPuntosConsolidados.setText("Puntos consolidados: "+ puntosConsolidados);
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

    public void incrementarTextoCantidadDeContestadas() {

        textoContadorDePreguntas.setText(respuestasCorrectasContestadas + "/10");
    }
    public void guardarPuntuacion() {
        MainActivity.music.stop();
        if(respuestasCorrectasContestadas == 10) {
            SavePoints(puntosTotales);
        }
        else {
            SavePoints(puntosConsolidados);
        }
        mCountDownTimer.cancel();
        Intent intent = new Intent(IUretoPregunta.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void pantalla_final(){
        pantalla_final.setVisibility(View.VISIBLE);
        botonRespuesta1.setClickable(false);
        botonRespuesta2.setClickable(false);
        botonRespuesta3.setClickable(false);
        botonRespuesta4.setClickable(false);

    }
    public void abandonOnClick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estas seguro que quieres abandonar? Obtendras los puntos consolidados")
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        guardarPuntuacion();
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

    public void clickBotonTerminarPartida(View v){

        Intent intent = new Intent(IUretoPregunta.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
