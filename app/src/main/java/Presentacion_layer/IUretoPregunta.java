package Presentacion_layer;

import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.util.List;

import Builder.CreadorDeJuego;
import Builder.Juego;
import Builder.JuegoBuilder;
import Builder.JuegoRetoPregunta;
import Domain_Layer.Question;

public class IUretoPregunta extends AppCompatActivity {

    private static final String DIFICULTAD_FACIL  = "1";
    private static final String DIFICULTAD_MEDIA  = "2";
    private static final String DIFICULTAD_DIFICIL  = "3";


    Button botonRespuesta1 = (Button) findViewById(R.id.botonRespuesta1);
    Button botonRespuesta2 = (Button) findViewById(R.id.botonRespuesta2);
    Button botonRespuesta3 = (Button) findViewById(R.id.botonRespuesta3);
    Button botonRespuesta4 = (Button) findViewById(R.id.botonRespuesta4);
    Question preguntaActual = new Question();

    List<Question> listaPreguntasDifultad1 = preguntaActual.getQuestionListByDifficulty(DIFICULTAD_FACIL);
    List<Question> listaPreguntasDifultad2 = preguntaActual.getQuestionListByDifficulty(DIFICULTAD_MEDIA);
    List<Question> listaPreguntasDifultad3 = preguntaActual.getQuestionListByDifficulty(DIFICULTAD_DIFICIL);
    private TextView textoPregunta = null;

    int respuestasCorrectasContestadas = 0;

    int vida = 1;

    JuegoBuilder retoPegunta = new JuegoRetoPregunta();
    CreadorDeJuego creadorDeJuego = new CreadorDeJuego();





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);


        creadorDeJuego.setJuegoBuilder(retoPegunta);
        creadorDeJuego.construirJuego();
        Juego juego = retoPegunta.getJuego();

        textoPregunta = findViewById(R.id.textoPregunta);

        int indice = preguntaActual.numeroAleatorioDeLista(listaPreguntasDifultad1);

        preguntaActual = listaPreguntasDifultad1.get(indice);

        textoPregunta.setText(preguntaActual.getStatement());

        botonRespuesta1.setText(preguntaActual.getAnswers().get(0).getText());
        botonRespuesta2.setText(preguntaActual.getAnswers().get(1).getText());
        botonRespuesta3.setText(preguntaActual.getAnswers().get(2).getText());
        botonRespuesta4.setText(preguntaActual.getAnswers().get(3).getText());

        //SET TIMER CON EL BUILDER

    }

    public int botonSelecionado(){

        if(botonRespuesta1.isPressed()){
            return 0;
        }else if(botonRespuesta2.isPressed()){
            return 1;
        } else if (botonRespuesta3.isPressed()) {
            return 2;
        } else if (botonRespuesta4.isPressed()) {
            return 3;
        }
        return 0;

    }

  public void botonClicked(View v){
        int preguntaEscogida = botonSelecionado();

        if(preguntaActual.getAnswers().get(preguntaEscogida).isCorrect()){
            // respuesta correcta
        } else{
            //respuesta Incorrecta
        }
  }

}
