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
    ImageView ods;
    Button botonRespuesta2;
    Button botonRespuesta3;
    Button botonRespuesta4;
    TextView textoPuntosTotal;
    Button botonConsolidar;
    Button botonSiguientePregunta;
    TextView textoPuntosGanados;
    TextView textoContadorDePreguntas;
    QuestionRepository preguntasEnBD ;
    Question preguntaActual = new Question();
    List<Answer> respuestasActuales;
    ProgressBar timeBar;
    List<Question> listaPreguntasDifultad1;
    List<Question> listaPreguntasDifultad2;
    List<Question> listaPreguntasDifultad3;
    Thread timeBarThread;
    Juego juego;
    TextView textoPregunta = null;
    int respuestasCorrectasContestadas = 0;
    int vida = 1;
    boolean respuestaEscogida;
    int puntosTotales = 0;

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
        botonSiguientePregunta = (Button) findViewById(R.id.botonSiguientePregunta);
        textoContadorDePreguntas = findViewById(R.id.textoContadorDePreguntas);

        //------builder--------
        JuegoBuilder retoPegunta = new JuegoRetoPregunta();
        CreadorDeJuego creadorDeJuego = new CreadorDeJuego();
        creadorDeJuego.setJuegoBuilder(retoPegunta);
        creadorDeJuego.construirJuego();
        juego = retoPegunta.getJuego();

        //juego.setNivel(2);
        //System.out.println(juego.getPuntos());

        startTimer();
    }

    private void IniciarBaseDedatos() {
        new Thread(new Runnable() {
            public void run(){
                try {


                    preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    listaPreguntasDifultad1 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_FACIL);
                    listaPreguntasDifultad2 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_MEDIA);
                    listaPreguntasDifultad3 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_DIFICIL);
                    preguntaActual = listaPreguntasDifultad1.get(1);
                    respuestasActuales = preguntasEnBD.getAnswers(preguntaActual);

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
        //fondo_transparente = findViewById(R.id.fondo_t);
        textoPuntosTotal= findViewById(R.id.puntosTotal);
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
        poner_imagen_ods();


 
    }
    public void poner_imagen_ods(){
        int numeroOds = preguntaActual.getOds();
        int imagenId = getResources().getIdentifier("ods_" + numeroOds, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(imagenId);
        ods.setImageDrawable(imagen);
    }

    private void startTimer() {
        int tiempo = juego.getTiempo();
        timeBar = findViewById(R.id.timeBar);
        timeBar.setMax(tiempo); timeBar.setProgress(tiempo);
        timeBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        Runnable timeBarCode = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < tiempo; i++) {
                        try { wait(1); }
                        catch (InterruptedException e) { return; }
                        if (i == tiempo / 3)   {
                            timeBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                        }
                        if (i == tiempo*2 / 3) { timeBar.setProgressTintList(ColorStateList.valueOf(Color.RED)); }
                        timeBar.incrementProgressBy(-1);
                    }
                    wrongAnswer("Se acabó el tiempo", -1);
                }
            }
        };
        timeBarThread = new Thread(timeBarCode);
        timeBarThread.start();
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

    public void metodoBotonSiguiente(View v) throws SQLException {
        if(respuestasCorrectasContestadas<=4){
            preguntaActual = listaPreguntasDifultad1.get(2);
        }else if(respuestasCorrectasContestadas>4 && respuestasCorrectasContestadas<=7){
            preguntaActual = listaPreguntasDifultad2.get(0);
        }else{
            preguntaActual = listaPreguntasDifultad3.get(0);
        }
        //respuestasActuales = preguntasEnBD.getAnswers(preguntaActual);
        ponerTextoEnPantalla();
        quitarPantallaAciertoFallo();
        poner_imagen_ods();
    }

    public void onClick (View view){
        this.timeBarThread.interrupt();
        int indiceProvisional = botonSeleccionado();

        if(respuestasActuales.get(indiceProvisional).isCorrect()){
            respuestasCorrectasContestadas++;
            correctAnswer("+100", indiceProvisional);
            incrementarTextoCantidadDeContestadas();
        }else {
            wrongAnswer("-200", indiceProvisional);
        }

        /*contenedor.setVisibility(View.VISIBLE);
        botonRespuesta1.setOnClickListener(null);
        botonRespuesta2.setOnClickListener(null);
        botonRespuesta3.setOnClickListener(null);
        botonRespuesta4.setOnClickListener(null);*/
        pantallaAciertoFallo();
    }
    public void pantallaAciertoFallo(){
        contenedor.setVisibility(View.VISIBLE);
        botonRespuesta1.setClickable(false);
        botonRespuesta2.setClickable(false);
        botonRespuesta3.setClickable(false);
        botonRespuesta4.setClickable(false);

    }
    public void quitarPantallaAciertoFallo(){
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
    public void correctAnswer(String screenText, int index) {
        textoPuntosGanados.setText(screenText);
        cambiarColorAVerde(index);
    }
    public void wrongAnswer(String screenText, int index) {
        textoPuntosGanados.setText(screenText);
        cambiarColorARojo(index);
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

    public void incrementarTextoCantidadDeContestadas(){

        textoContadorDePreguntas.setText(respuestasCorrectasContestadas +"/10");
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

}
