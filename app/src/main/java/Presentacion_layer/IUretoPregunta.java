package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import java.util.List;

import Builder.CreadorDeJuego;
import Builder.Juego;
import Builder.JuegoBuilder;
import Builder.JuegoRetoPregunta;
import Domain_Layer.Answer;
import Domain_Layer.Question;
import Persistence.QuestionRepository;

public class IUretoPregunta extends AppCompatActivity {

    private static final String DIFICULTAD_FACIL  = "Baja";
    private static final String DIFICULTAD_MEDIA  = "Media";
    private static final String DIFICULTAD_DIFICIL  = "Alta";

    ImageView fondo_transparente;
    RelativeLayout contenedor;
    Button botonRespuesta1;
    Button botonRespuesta2;
    Button botonRespuesta3;
    Button botonRespuesta4;
    TextView puntosTotal;
    Button botonConsolidar;
    Button botonSiguientePregunta;
    TextView puntosGanados;

    Question preguntaActual = new Question();

    List<Answer> respuestasActuales;

    ProgressBar timeBar;

    List<Question> listaPreguntasDifultad1;
    List<Question> listaPreguntasDifultad2;
    List<Question> listaPreguntasDifultad3;

    Runnable timeBarThread;

    Juego juego;
    private TextView textoPregunta = null;

    int respuestasCorrectasContestadas = 0;

    int vida = 1;

    boolean respuestaEscogida;

    public IUretoPregunta() throws SQLException {
    }

    //  JuegoBuilder retoPegunta = new JuegoRetoPregunta();
    //CreadorDeJuego creadorDeJuego = new CreadorDeJuego();





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);

        IniciarBaseDedatos();

        startTimer();


        textoPregunta = findViewById(R.id.textoPregunta);
        puntosGanados = findViewById(R.id.puntosGanados);
        contenedor = findViewById(R.id.contenedor_resp);
        fondo_transparente = findViewById(R.id.fondo_t);
        puntosTotal = findViewById(R.id.puntosTotal);
        botonRespuesta1 = (Button) findViewById(R.id.botonRespuesta1);
        botonRespuesta2 = (Button) findViewById(R.id.botonRespuesta2);
        botonRespuesta3 = (Button) findViewById(R.id.botonRespuesta3);
        botonRespuesta4 = (Button) findViewById(R.id.botonRespuesta4);
        botonConsolidar = (Button) findViewById(R.id.botonConsolidar);
        botonSiguientePregunta = (Button) findViewById(R.id.botonSiguientePregunta);




        //------builder--------
        JuegoBuilder retoPegunta = new JuegoRetoPregunta();
        CreadorDeJuego creadorDeJuego = new CreadorDeJuego();
        creadorDeJuego.setJuegoBuilder(retoPegunta);
        //creadorDeJuego.construirJuego();
        Juego juego = retoPegunta.getJuego();


        startTimer();


    }
        private void IniciarBaseDedatos() {
        new Thread(new Runnable() {
            public void run(){
                try {


                    QuestionRepository preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    listaPreguntasDifultad1 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_FACIL);
                    listaPreguntasDifultad2 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_MEDIA);
                    listaPreguntasDifultad3 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_DIFICIL);
                    preguntaActual = listaPreguntasDifultad1.get(0);
                    respuestasActuales = preguntasEnBD.getAnswers(preguntaActual);

                    System.out.println(listaPreguntasDifultad1);
                    System.out.println(listaPreguntasDifultad2);
                    System.out.println(listaPreguntasDifultad3);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ponerTextoEnPantalla();
                        }
                    });


                }
                catch(Exception e){

                    System.out.println(e);
                }
            }
        }).start();


        contenedor = findViewById(R.id.contenedor_resp);
        fondo_transparente = findViewById(R.id.fondo_t);
        puntosTotal= findViewById(R.id.puntosTotal);
        botonRespuesta1 = (Button) findViewById(R.id.botonRespuesta1);
        botonRespuesta2 = (Button) findViewById(R.id.botonRespuesta2);
        botonRespuesta3 = (Button) findViewById(R.id.botonRespuesta3);
        botonRespuesta4 = (Button) findViewById(R.id.botonRespuesta4);

        // creadorDeJuego.setJuegoBuilder(retoPegunta);
        //creadorDeJuego.construirJuego();
        //Juego juego = retoPegunta.getJuego();



       textoPregunta = findViewById(R.id.textoPregunta);


        //preguntaActual.numeroAleatorioDeLista(listaPreguntasDifultad1);
        /*
        try {
            Thread.sleep(10000); // pausa el hilo actual durante 10 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */



        //SET TIMER CON EL BUILDER
    }

    private void ponerTextoEnPantalla() {
        int indice = 0;


        textoPregunta.setText(preguntaActual.getStatement());

        botonRespuesta1.setText(respuestasActuales.get(0).getText());
        botonRespuesta2.setText(respuestasActuales.get(1).getText());
        botonRespuesta3.setText(respuestasActuales.get(2).getText());
        botonRespuesta4.setText(respuestasActuales.get(3).getText());

 
    }

    private void startTimer() {
        int tiempo = juego.getTiempo();
        timeBar = findViewById(R.id.timeBar);
        timeBar.setMax(tiempo); timeBar.setProgress(tiempo);
        timeBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
         timeBarThread = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < tiempo/*retoPegunta.getJuego().getTiempo()*/; i++) {
                        try { wait(1); }
                        catch (InterruptedException e) { throw new RuntimeException(e); }
                        if (i == tiempo / 3)   {
                            timeBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                        }
                        if (i == tiempo*2 / 3) { timeBar.setProgressTintList(ColorStateList.valueOf(Color.RED)); }
                        timeBar.incrementProgressBy(-1);
                    }
                }
            }
        };
        new Thread(timeBarThread).start();
    }

    public int botonSeleccionado(){

        int indice = 0;
        if(botonRespuesta1.isPressed()){
            indice = 0;
           // respuestaEscogida = respuestasActuales.get(0).isCorrect();
        }else if(botonRespuesta2.isPressed()){
            indice = 1;
            //respuestaEscogida = respuestasActuales.get(1).isCorrect();
        } else if (botonRespuesta3.isPressed()) {
            indice = 2;
           // respuestaEscogida = respuestasActuales.get(2).isCorrect();
        } else if (botonRespuesta4.isPressed()) {
            indice = 3;
            //respuestaEscogida = respuestasActuales.get(3).isCorrect();
        }

        return  indice;
        //return respuestaEscogida;

    }

    public void onClick (View view){
        int indiceProvisional = botonSeleccionado();
       // respuestaEscogida = respuestasActuales.get(indiceProvisional).isCorrect();

        if(respuestasActuales.get(indiceProvisional).isCorrect()){
            //campion de pantallan

            puntosGanados.setText("+100");

            cambiarColorAVerde(indiceProvisional);

            }else {

            puntosGanados.setText("-200");
            cambiarColorARojo(indiceProvisional);
        }


    }

    public void cambiarColorARojo(int botonEscogido){

        if(botonEscogido == 0){
            botonRespuesta1.setBackground(getDrawable(R.drawable.boton_rojo));
        }else if(botonEscogido == 1){
            botonRespuesta2.setBackground(getDrawable(R.drawable.boton_rojo));
        }else if(botonEscogido == 2){
            botonRespuesta3.setBackground(getDrawable(R.drawable.boton_rojo));
        }else if(botonEscogido == 3){
            botonRespuesta4.setBackground(getDrawable(R.drawable.boton_rojo));
        }

    }

    public void cambiarColorAVerde(int botonEscogido){

        if(botonEscogido == 0){
            botonRespuesta1.setBackground(getDrawable(R.drawable.boton_verde));
        }else if(botonEscogido == 1){
            botonRespuesta2.setBackground(getDrawable(R.drawable.boton_verde));
        }else if(botonEscogido == 2){
            botonRespuesta3.setBackground(getDrawable(R.drawable.boton_verde));
        }else if(botonEscogido == 3){
            botonRespuesta4.setBackground(getDrawable(R.drawable.boton_verde));
        }

    }

    public void abandonOnClick(View view) { //metodo si el boton ABANDONAR se pulsa
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estas seguro que quieres abandonar?")
                .setMessage("")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica cuando se presiona el botón Aceptar
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica cuando se presiona el botón Cancelar
                    }
                });
        alert.setCancelable(true);

        AlertDialog dialog = alert.create(); dialog.show();
    }
//----------------Esto es de Raquel--------------------
            //contenedor.setVisibility(View.VISIBLE);
            //botonRespuesta2.setClickable(false);
}
