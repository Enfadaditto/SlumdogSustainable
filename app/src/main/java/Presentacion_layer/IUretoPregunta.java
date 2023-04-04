package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class IUretoPregunta extends AppCompatActivity {

    private static final String DIFICULTAD_FACIL = "Baja";
    private static final String DIFICULTAD_MEDIA = "Media";
    private static final String DIFICULTAD_DIFICIL = "Alta";

    ImageView fondo_transparente;
    RelativeLayout contenedor;
    Button botonRespuesta1;
    ImageView ods;
    ImageView imagenCorazon;
    Button botonRespuesta2;
    Button botonRespuesta3;
    Button botonRespuesta4;
    TextView textoPuntosTotal;
    Button botonConsolidar;
    Button botonSiguientePregunta;
    TextView textoPuntosGanados, textoContadorDePreguntas;
    QuestionRepository preguntasEnBD;
    Question preguntaActual = new Question();
    List<Answer> respuestasActuales;
    int i = 0,j = 0,k = 0;
    List<Answer> listaRespuestas;
    ProgressBar timeBar;
    List<Question> listaPreguntasDifultad1, listaPreguntasDifultad2, listaPreguntasDifultad3;
    Thread timeBarThread;
    Juego juego;
    TextView textoPregunta = null;
    int respuestasCorrectasContestadas = 0;
    int vida = 1;
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


        textoPregunta = findViewById(R.id.textoPregunta);
        textoPuntosGanados = findViewById(R.id.puntosGanados);
        contenedor = findViewById(R.id.contenedor_resp);
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

        //-------builder--------//
        JuegoBuilder retoPegunta = new JuegoRetoPregunta();
        CreadorDeJuego creadorDeJuego = new CreadorDeJuego();
        creadorDeJuego.setJuegoBuilder(retoPegunta);
        creadorDeJuego.construirJuego();
        juego = retoPegunta.getJuego();


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
                    preguntaActual = listaPreguntasDifultad1.get(i++);
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

        textoPregunta.setText(preguntaActual.getStatement());

        botonRespuesta1.setText(respuestasActuales.get(0).getText());
        botonRespuesta2.setText(respuestasActuales.get(1).getText());
        botonRespuesta3.setText(respuestasActuales.get(2).getText());
        botonRespuesta4.setText(respuestasActuales.get(3).getText());
        poner_imagen_ods();

    }

    public void poner_imagen_ods() {
        int numeroOds = preguntaActual.getOds();
        int imagenId = getResources().getIdentifier("ods_" + numeroOds, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(imagenId);
        ods.setImageDrawable(imagen);
    }

    private void startTimer() {
        int tiempo =juego.getTiempo();
        timeBar = findViewById(R.id.timeBar);
        timeBar.setMax(tiempo);
        timeBar.setProgress(tiempo);
        timeBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
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
    }

    public int botonSeleccionado() {
        int indice = 0;
        if (botonRespuesta1.isPressed()) {
            indice = 0;
            // respuestaEscogida = respuestasActuales.get(0).isCorrect();
        } else if (botonRespuesta2.isPressed()) {
            indice = 1;
            //respuestaEscogida = respuestasActuales.get(1).isCorrect();
        } else if (botonRespuesta3.isPressed()) {
            indice = 2;
            // respuestaEscogida = respuestasActuales.get(2).isCorrect();
        } else if (botonRespuesta4.isPressed()) {
            indice = 3;
            //respuestaEscogida = respuestasActuales.get(3).isCorrect();
        }

        return indice;
        //return respuestaEscogida;

    }

    public void metodoBotonSiguiente(View v) throws SQLException {
        if (respuestasCorrectasContestadas <= 4) {
            preguntaActual = listaPreguntasDifultad1.get(i++);
        } else if (respuestasCorrectasContestadas > 4 && respuestasCorrectasContestadas <= 7) {
            nivel = 2;
            preguntaActual = listaPreguntasDifultad2.get(j++);
        } else {
            nivel = 3;
            preguntaActual = listaPreguntasDifultad3.get(k++);
        }
        respuestasActuales = getRespuestasPregunta(preguntaActual);
        ponerTextoEnPantalla();
        quitarPantallaAciertoFallo();
        poner_imagen_ods();
        startTimer();
    }

    public void onClick(View view) {
        this.timeBarThread.interrupt();
        int indiceProvisional = botonSeleccionado();

        if (respuestasActuales.get(indiceProvisional).isCorrect()) {
            correctAnswer(juego.getPuntos() * nivel, indiceProvisional);
        } else {
            wrongAnswer(juego.getPuntos() * nivel * (-2), indiceProvisional);
        }

        textoPuntosTotal.setText("Puntos Totales = " + puntosTotales);
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
        respuestasCorrectasContestadas++;
        puntosTotales += juego.getPuntos() * nivel;
        textoPuntosGanados.setText("+"+screenText + "");
        textoPuntosTotal.setText("Puntos Totales = " + puntosTotales);
        cambiarColorAVerde(index);
        incrementarTextoCantidadDeContestadas();
        visualizacionBotonConsolidar(true);

        pantallaAciertoFallo();
    }

    public void wrongAnswer(int screenText, int index) {
        vida--;
        if (vida < 0) {
            puntosTotales = 0;
            puntosConsolidados = 0;
            System.out.println("--------GAME OVER-------------");
            Intent intent = new Intent(IUretoPregunta.this, MainActivity.class); //Creo que esto tiene que llevar una pantalla de GAMEOVER
            startActivity(intent);
            finish();

            return;
        }

        puntosTotales -= juego.getPuntos() * nivel * 2;
        if (puntosTotales < 0) puntosTotales = 0;
        textoPuntosGanados.setText(screenText + "");
        if (screenText == 2) {
            textoPuntosGanados.setText("Se acabo el tiempo");
        }
        if (index != -1) cambiarColorARojo(index);

        textoPuntosTotal.setText("Puntos Totales = " + puntosTotales);
        visualizacionBotonConsolidar(false);

        //imagenCorazon.setBackground(getDrawable(R.drawable.corazon_roto)); //Aqui va corazon roto
        imagenCorazon.setImageDrawable(getDrawable(R.drawable.corazon_roto));
        pantallaAciertoFallo();
    }

    public void visualizacionBotonConsolidar(Boolean respuestaCorrecta) {

        if (respuestaCorrecta && !haConsolidado) {
            botonConsolidar.setVisibility(View.VISIBLE);
        } else {
            botonConsolidar.setVisibility(View.INVISIBLE);
        }

    }

    public void clickBotonConsolidar(View v) {

        haConsolidado = true;
        botonConsolidar.setBackground(getDrawable(R.drawable.boton_verde));
        puntosConsolidados = puntosTotales;

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

    public void abandonOnClick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estas seguro que quieres abandonar?")
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(IUretoPregunta.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
}
