package Presentacion_layer;

import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.util.List;

import com.slumdogsustainable.Domain_Layer.Question;

public class IUretoPregunta extends AppCompatActivity {

    private static final String DIFICULTAD_FACIL  = "1";
    private static final String DIFICULTAD_MEDIA  = "2";
    private static final String DIFICULTAD_DIFICIL  = "3";


    Button botonRespuesta1 = (Button) findViewById(R.id.botonRespuesta1);
    Button botonRespuesta2 = (Button) findViewById(R.id.botonRespuesta2);
    Button botonRespuesta3 = (Button) findViewById(R.id.botonRespuesta3);
    Button botonRespuesta4 = (Button) findViewById(R.id.botonRespuesta4);
    Question preguntaActual = new Question();

    List<Question> ListaPreguntasDifultad1 = preguntaActual.getQuestionListByDifficulty(DIFICULTAD_FACIL);
    List<Question> ListaPreguntasDifultad2 = preguntaActual.getQuestionListByDifficulty(DIFICULTAD_MEDIA);
    List<Question> ListaPreguntasDifultad3 = preguntaActual.getQuestionListByDifficulty(DIFICULTAD_DIFICIL);
    private TextView textoPregunta;

    int respuestasCorrectasContestadas = 0;

    int vida = 1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_pregunta);

        textoPregunta = findViewById(R.id.textoPreunta);


    }

}
