package Presentacion_layer;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import Builder.BuilderPartidaRetoAhorcado;
import Builder.BuilderPartidaRetoPregunta;
import Builder.CreadorDePartida;
import Domain_Layer.Partida;
import Domain_Layer.PartidaRetoAhorcado;
import Domain_Layer.PartidaRetoPregunta;
import Domain_Layer.User;
import Persistence.ODS_URepository;

public class MediadorDeRetos extends AppCompatActivity {
    int vidas = 1;
    int ronda = 1;

    TextView textoPrueba1;
    TextView textoPrueba2;
    int indicePreguntasFacil = 0;

    int indicePreguntasDificil = 0;

    int indicePreguntasMedio = 0;
    PartidaRetoPregunta juegoRetoPregunta;
    PartidaRetoAhorcado juegoRetoAhorcado;

    int puntosTotales;

    int puntosConsolidados;
    Button botonRetoPregunta;
    Button botonRetoAhorcado;
    Button botonRetoFrase;
    Button botonRetoMixto;
    boolean retoPreguntaEscogido = false;
    boolean retoAhorcadoEscogido = false;
    boolean retoMixtoEscogido;
    int erroresRetoAhorcado;

    boolean haConsolidado = false;
    public final static int REQUESTCODE = 100;
    BuilderPartidaRetoPregunta retoPregunta;
    BuilderPartidaRetoAhorcado retoAhorcado;

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_reto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        botonRetoAhorcado = findViewById(R.id.botonRetoAhorcado);
        botonRetoPregunta = findViewById(R.id.botonRetoPregunta);
        botonRetoFrase = findViewById(R.id.botonRetoFrase);
        botonRetoMixto = findViewById(R.id.botonRetoMixto);



    }

    public void clickBotonRetoAhorcado(View v){
        retoAhorcado = new BuilderPartidaRetoAhorcado();
        CreadorDePartida creadorDeJuego = new CreadorDePartida();
        creadorDeJuego.setJuegoBuilder(retoAhorcado);
        creadorDeJuego.construirJuego();
        juegoRetoAhorcado= retoAhorcado.getJuego();
        siguienteRetoAhorcado();
       // Intent intent = new Intent(MediadorDeRetos.this, IUretoAhorcado.class);
        //startActivity(intent);

    }
    public void clickBotonRetoPregunta(View v){
        retoPreguntaEscogido = true;
        retoPregunta = new BuilderPartidaRetoPregunta();
        CreadorDePartida creadorDeJuego = new CreadorDePartida();
        creadorDeJuego.setJuegoBuilder(retoPregunta);
        creadorDeJuego.construirJuego();
        juegoRetoPregunta = retoPregunta.getJuego();
        siguienteRetoPregunta();
    }

    public void siguienteRetoPregunta() {
        if(vidas < 0 || ronda > 10) {finish();}
        else if(ronda <= 4){
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel1().get(indicePreguntasFacil++));
        }

        else if(ronda > 4 && ronda <= 7) {
            juegoRetoPregunta.setNivel(2);
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel2().get(indicePreguntasMedio++));
        }

        else {
            juegoRetoPregunta.setNivel(3);
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel3().get(indicePreguntasDificil++));
        }
        pasarDatosRetoPregunta();
    }

    public void siguienteRetoAhorcado() {
        if(vidas < 0 || ronda > 10) {finish();}
        else if(ronda <= 4){
            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabrasNivel1().get(indicePreguntasFacil++));
            juegoRetoAhorcado.setErroresRetoAhorcado(0);
        }

        else if(ronda > 4 && ronda <= 7) {
            juegoRetoPregunta.setNivel(2);
            juegoRetoAhorcado.setErroresRetoAhorcado(3);
            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabrasNivel2().get(indicePreguntasMedio++));
        }

        else {
            juegoRetoPregunta.setNivel(3);
            juegoRetoAhorcado.setErroresRetoAhorcado(5);
            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabrasNivel3().get(indicePreguntasDificil++));
        }
        pasarDatosRetoAhorcado();
    }

    public void pasarDatosRetoAhorcado(){
        Intent I = new Intent(getApplicationContext(), IUretoAhorcado.class);
        Bundle b = new Bundle();
        b.putString("palabraAhorcado", juegoRetoAhorcado.getAhorcado().getPalabra());
        b.putString("enunciadoAhorcado", juegoRetoAhorcado.getAhorcado().getEnunciado());
        b.putInt("Tiempo", juegoRetoAhorcado.getTiempo());
        b.putInt("Vidas", vidas);
        b.putInt("erroresRetoAhorcado", juegoRetoAhorcado.getErroresRetoAhorcado());
        b.putInt("odsAhorcado", juegoRetoAhorcado.getAhorcado().getId_ODS());
        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }

    public void pasarDatosRetoPregunta() {
        Intent I = new Intent(getApplicationContext(), IUretoPregunta.class);
        Bundle b = new Bundle();
        b.putInt("idPregunta", juegoRetoPregunta.getPreguntaActual().getQuestionID());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juegoRetoPregunta.getTiempoOpcion());
        b.putInt("Tiempo", juegoRetoPregunta.getTiempo());
        b.putInt("Nivel", juegoRetoPregunta.getNivel());
        b.putInt("SonidoFallo", juegoRetoPregunta.getSonidofallo());
        b.putInt("SonidoAcierto", juegoRetoPregunta.getSonidoacierto());
        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE) {
            if(resultCode == RESULT_OK) {
                puntosTotales += juegoRetoPregunta.getPuntos() * juegoRetoPregunta.getNivel();
                ronda++;
                updateHitsFailsODS(true, juegoRetoPregunta.getPreguntaActual().getOds(), MainActivity.user);
            }
            else if(resultCode == RESULT_CANCELED) {
                puntosTotales += juegoRetoPregunta.getPuntos() * juegoRetoPregunta.getNivel() * (-2);
                if(puntosTotales < 0) {puntosTotales = 0;}
                vidas--;
                updateHitsFailsODS(false, juegoRetoPregunta.getPreguntaActual().getOds(), MainActivity.user);
            }
            else if(resultCode == RESULT_FIRST_USER) {
                puntosTotales += juegoRetoPregunta.getPuntos() * juegoRetoPregunta.getNivel();
                puntosConsolidados = puntosTotales;
                haConsolidado = true;
                ronda++;
                updateHitsFailsODS(true, juegoRetoPregunta.getPreguntaActual().getOds(), MainActivity.user);
            }
            siguienteRetoPregunta();
        }
    }
    public void updateHitsFailsODS(boolean hit, int idODS, User u) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ODS_URepository ODS = new ODS_URepository(MainActivity.conexion);
                ODS.updateODS(hit, idODS, u);
            }
        }).start();
    }
}
