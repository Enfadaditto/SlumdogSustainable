package Presentacion_layer;

import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.sql.SQLException;
import java.util.List;

import Builder.CreadorDeJuego;
import Builder.Juego;
import Builder.JuegoBuilder;
import Builder.JuegoRetoPregunta;
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
    TextView puntos_totales_tx;

    Question preguntaActual = new Question();

    ProgressBar timeBar;

    private TextView textoPregunta = null;

    int respuestasCorrectasContestadas = 0;

    int vida = 1;

    public IUretoPregunta() throws SQLException {
    }

    //  JuegoBuilder retoPegunta = new JuegoRetoPregunta();
    //CreadorDeJuego creadorDeJuego = new CreadorDeJuego();





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);

        //Con esto ya puedes obtener las preguntas :)
        new Thread(new Runnable() {
            public void run(){
                try {
                    QuestionRepository preguntasEnBD = new QuestionRepository();
                    List<Question> listaPreguntasDifultad1 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_FACIL);
                    List<Question> listaPreguntasDifultad2 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_MEDIA);
                    List<Question> listaPreguntasDifultad3 = preguntasEnBD.getQuestionListByDifficulty(DIFICULTAD_DIFICIL);

                    System.out.println(listaPreguntasDifultad1);
                    System.out.println(listaPreguntasDifultad2);
                    System.out.println(listaPreguntasDifultad3);
                }
                catch(Exception e){

                    System.out.println(e);
                }
            }
        }).start();


        contenedor = findViewById(R.id.contenedor_resp);
        fondo_transparente = findViewById(R.id.fondo_t);
        puntos_totales_tx = findViewById(R.id.puntos_total);
        botonRespuesta1 = (Button) findViewById(R.id.botonRespuesta1);
        botonRespuesta2 = (Button) findViewById(R.id.botonRespuesta2);
        botonRespuesta3 = (Button) findViewById(R.id.botonRespuesta3);
        botonRespuesta4 = (Button) findViewById(R.id.botonRespuesta4);

        timeBar = findViewById(R.id.timeBar);

        Runnable timeBarThread = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 10000/*retoPegunta.getJuego().getTiempo()*/; i++) {
                        try { wait(1); }
                        catch (InterruptedException e) { throw new RuntimeException(e); }
                        timeBar.incrementProgressBy(-1);
                    }
                }
            }
        };

        new Thread(timeBarThread).start();

       // creadorDeJuego.setJuegoBuilder(retoPegunta);
        //creadorDeJuego.construirJuego();
        //Juego juego = retoPegunta.getJuego();

/*
       textoPregunta = findViewById(R.id.textoPregunta);

        int indice = 0;
                //preguntaActual.numeroAleatorioDeLista(listaPreguntasDifultad1);

        preguntaActual = listaPreguntasDifultad1.get(indice);

        textoPregunta.setText(preguntaActual.getStatement());

        botonRespuesta1.setText(preguntaActual.getAnswers().get(0).getText());
        botonRespuesta2.setText(preguntaActual.getAnswers().get(1).getText());
        botonRespuesta3.setText(preguntaActual.getAnswers().get(2).getText());
        botonRespuesta4.setText(preguntaActual.getAnswers().get(3).getText());
*/

        //SET TIMER CON EL BUILDER
    }

    public boolean botonSeleccionado(){

        int respuestaEscogida  = 0;
        if(botonRespuesta1.isPressed()){
            respuestaEscogida = 0;
        }else if(botonRespuesta2.isPressed()){
            respuestaEscogida = 1;
            contenedor.setVisibility(View.VISIBLE);
            botonRespuesta2.setClickable(false);
        } else if (botonRespuesta3.isPressed()) {
            respuestaEscogida =  2;
        } else if (botonRespuesta4.isPressed()) {
             respuestaEscogida = 3;
        }

        return true;
                //preguntaActual.getAnswers().get(respuestaEscogida).isCorrect();

    }

    public void onClick (View view){
        if(botonSeleccionado()){
            //campion de pantallan

        }
    }


}
