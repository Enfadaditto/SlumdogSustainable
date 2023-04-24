package Presentacion_layer;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import Builder.BuilderPartidaRetoPregunta;
import Builder.CreadorDePartida;
import Domain_Layer.PartidaRetoPregunta;

public class MediadorDeRetos extends AppCompatActivity {
    int vidas = 1;
    int ronda = 1;

    int indicePreguntasFacil = 0;

    int indicePreguntasDificil = 0;

    int indicePreguntasMedio = 0;
    PartidaRetoPregunta juego;
    int puntosTotales;

    int puntosConsolidados;
    Button botonRetoPregunta;
    Button botonRetoAhorcado;
    Button botonRetoFrase;
    Button botonRetoMixto;

    boolean haConsolidado = false;
    public final static int REQUESTCODE = 100;
    BuilderPartidaRetoPregunta retoPregunta;

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_reto);

        botonRetoAhorcado = findViewById(R.id.botonRetoAhorcado);
        botonRetoPregunta = findViewById(R.id.botonRetoPregunta);
        botonRetoFrase = findViewById(R.id.botonRetoFrase);
        botonRetoMixto = findViewById(R.id.botonRetoMixto);




    }


    public void clickBotonRetoPregunta(View v){
        retoPregunta = new BuilderPartidaRetoPregunta();
        CreadorDePartida creadorDeJuego = new CreadorDePartida();
        creadorDeJuego.setJuegoBuilder(retoPregunta);
        creadorDeJuego.construirJuego();
        juego = retoPregunta.getJuego();
        juego.setPreguntaActual(juego.getPreguntasNivel1().get(indicePreguntasFacil++));
        siguienteReto();
    }

    public void siguienteReto() {
        if(vidas < 0 || ronda > 10) {finish();}
        else if(ronda <= 4){
            juego.setPreguntaActual(juego.getPreguntasNivel1().get(indicePreguntasFacil++));
            pasarDatosRetoPregunta();
        }

        else if(ronda > 4 && ronda <= 7) {
            juego.setNivel(2);
            juego.setPreguntaActual(juego.getPreguntasNivel2().get(indicePreguntasMedio++));
            pasarDatosRetoPregunta();
        }

        else {
            juego.setNivel(3);
            juego.setPreguntaActual(juego.getPreguntasNivel3().get(indicePreguntasDificil++));
            pasarDatosRetoPregunta();
        }
    }

    public void pasarDatosRetoPregunta() {
        Intent I = new Intent(getApplicationContext(), IUretoPregunta.class);
        Bundle b = new Bundle();
        b.putInt("idPregunta", juego.getPreguntaActual().getQuestionID());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juego.getTiempoOpcion());
        b.putInt("Tiempo", juego.getTiempo());
        b.putInt("Nivel", juego.getNivel());
        b.putInt("SonidoFallo", juego.getSonidofallo());
        b.putInt("SonidoAcierto", juego.getSonidoacierto());
        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE) {
            if(resultCode == RESULT_OK) {
                puntosTotales += juego.getPuntos() * juego.getNivel();
                ronda++;
            }
            else if(resultCode == RESULT_CANCELED) {
                puntosTotales += juego.getPuntos() * juego.getNivel() * (-2);
                if(puntosTotales < 0) {puntosTotales = 0;}
                vidas--;
            }
            else if(resultCode == RESULT_FIRST_USER) {
                puntosTotales += juego.getPuntos() * juego.getNivel();
                puntosConsolidados = puntosTotales;
                haConsolidado = true;
                ronda++;
            }
            siguienteReto();
        }
    }

}
