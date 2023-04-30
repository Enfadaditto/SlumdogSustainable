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
import Builder.BuilderPartidaRetoDescubrirFrase;
import Builder.BuilderPartidaRetoPregunta;
import Builder.CreadorDePartida;
import Domain_Layer.Partida;
import Domain_Layer.PartidaRetoAhorcado;
import Domain_Layer.PartidaRetoDescubrirFrase;
import Domain_Layer.PartidaRetoPregunta;
import Domain_Layer.User;
import Persistence.ODS_URepository;
import Persistence.UserRepository;

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

    PartidaRetoDescubrirFrase juegoRetoDescubrirFrase;

    int puntosTotales;

    int puntosConsolidados;
    Button botonRetoPregunta;
    Button botonRetoAhorcado;
    Button botonRetoFrase;
    Button botonRetoMixto;
    boolean retoPreguntaEscogido = false;
    boolean retoAhorcadoEscogido = false;
    boolean retoDescubrirFraseEscogido = false;
    boolean retoMixtoEscogido;
    int erroresRetoAhorcado;

    boolean haConsolidado = false;
    public final static int REQUESTCODE = 100;
    BuilderPartidaRetoPregunta retoPregunta;
    BuilderPartidaRetoAhorcado retoAhorcado;
    BuilderPartidaRetoDescubrirFrase retoDescubrirFrase;

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_reto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        botonRetoAhorcado = findViewById(R.id.botonRetoAhorcado);
        botonRetoPregunta = findViewById(R.id.botonRetoPregunta);
        botonRetoFrase = findViewById(R.id.botonRetoFrase);
        botonRetoMixto = findViewById(R.id.botonRetoMixto);

    }

    public void updateGamesandTime(Boolean won, int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(MainActivity.conexion);
                u.updateGamesandTime(won, time);
            }
        }).start();
    }
    public void clickBotonRetoAhorcado(View v){
        MainActivity.music.stop();
        MainActivity.background.start();
        retoAhorcadoEscogido = true;
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
        MainActivity.music.stop();
        MainActivity.background.start();
        retoPreguntaEscogido = true;
        retoPregunta = new BuilderPartidaRetoPregunta();
        CreadorDePartida creadorDeJuego = new CreadorDePartida();
        creadorDeJuego.setJuegoBuilder(retoPregunta);
        creadorDeJuego.construirJuego();
        juegoRetoPregunta = retoPregunta.getJuego();
        siguienteRetoPregunta();
    }

    public void clickBotonRetoDescubrirFrase(View v){
        MainActivity.music.stop();
        MainActivity.background.start();
        retoDescubrirFraseEscogido = true;
        retoDescubrirFrase = new BuilderPartidaRetoDescubrirFrase();
        CreadorDePartida creadorDeJuego = new CreadorDePartida();
        creadorDeJuego.setJuegoBuilder(retoDescubrirFrase);
        creadorDeJuego.construirJuego();
        juegoRetoDescubrirFrase = retoDescubrirFrase.getJuego();
        siguienteRetoDescubrirFrase();
    }

    public void siguienteRetoPregunta() {
        if(ronda > 10) {
            updateGamesandTime(true, juegoRetoPregunta.getTiempo() * 10);
            finish();
            return;
        }
        if(vidas < 0) {
            updateGamesandTime(false, juegoRetoPregunta.getTiempo() * ronda);
            finish();
            return;
        }
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
        if(ronda > 10) {
            updateGamesandTime(true, juegoRetoAhorcado.getTiempo() * 10);
            finish();
            System.out.println("Entra aquí");
            return;
        }
        if(vidas < 0) {
            updateGamesandTime(false, juegoRetoAhorcado.getTiempo() * ronda);
            finish();
            System.out.println("O entra aquí");
            return;
        }
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

    public void siguienteRetoDescubrirFrase() {
        if(vidas < 0 || ronda > 10) {finish();}
        else if(ronda <= 4){
            juegoRetoDescubrirFrase.setNivel(1);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indicePreguntasFacil++));
        }

        else if(ronda > 4 && ronda <= 7) {
            juegoRetoDescubrirFrase.setNivel(2);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indicePreguntasMedio++));
        }

        else {
            juegoRetoDescubrirFrase.setNivel(3);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indicePreguntasDificil++));
        }
        pasarDatosRetoDescubrirFrase();
    }

    public void pasarDatosRetoAhorcado(){
        Intent I = new Intent(getApplicationContext(), IUretoAhorcado.class);
        Bundle b = new Bundle();
        b.putString("palabraAhorcado", juegoRetoAhorcado.getAhorcado().getPalabra());
        b.putString("enunciadoAhorcado", juegoRetoAhorcado.getAhorcado().getEnunciado());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juegoRetoAhorcado.getTiempoOpcion());
        b.putInt("Tiempo", juegoRetoAhorcado.getTiempo());
        b.putInt("Nivel", juegoRetoAhorcado.getNivel());
        b.putInt("SonidoFallo", juegoRetoAhorcado.getSonidofallo());
        b.putInt("SonidoAcierto", juegoRetoAhorcado.getSonidoacierto());
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

    public void pasarDatosRetoDescubrirFrase() {
        Intent I = new Intent(getApplicationContext(), IUretoFrase.class);
        Bundle b = new Bundle();
        b.putString("fraseEnunciado", juegoRetoDescubrirFrase.getFraseActual().getFrase());
        b.putString("descripcionEnunciado", juegoRetoDescubrirFrase.getFraseActual().getDescripcion());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juegoRetoDescubrirFrase.getTiempoOpcion());
        b.putInt("Tiempo", juegoRetoDescubrirFrase.getTiempo());
        b.putInt("Nivel", juegoRetoDescubrirFrase.getNivel());
        b.putInt("SonidoFallo", juegoRetoDescubrirFrase.getSonidofallo());
        b.putInt("SonidoAcierto", juegoRetoDescubrirFrase.getSonidoacierto());
        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(retoPreguntaEscogido) {
            if (requestCode == REQUESTCODE) {
                if (resultCode == RESULT_OK) {
                    puntosTotales += juegoRetoPregunta.getPuntos() * juegoRetoPregunta.getNivel();
                    ronda++;
                    updateHitsFailsODS(true, juegoRetoPregunta.getPreguntaActual().getOds(), MainActivity.user);
                } else if (resultCode == RESULT_CANCELED) {
                    puntosTotales += juegoRetoPregunta.getPuntos() * juegoRetoPregunta.getNivel() * (-2);
                    if (puntosTotales < 0) {
                        puntosTotales = 0;
                    }
                    vidas--;
                    updateHitsFailsODS(false, juegoRetoPregunta.getPreguntaActual().getOds(), MainActivity.user);
                } else if (resultCode == RESULT_FIRST_USER) {
                    puntosTotales += juegoRetoPregunta.getPuntos() * juegoRetoPregunta.getNivel();
                    puntosConsolidados = puntosTotales;
                    haConsolidado = true;
                    ronda++;
                    updateHitsFailsODS(true, juegoRetoPregunta.getPreguntaActual().getOds(), MainActivity.user);
                }
                siguienteRetoPregunta();
            }
        } else if (retoAhorcadoEscogido) {
            if (requestCode == REQUESTCODE) {
                if (resultCode == RESULT_OK) {
                    puntosTotales += juegoRetoAhorcado.getPuntos() * juegoRetoAhorcado.getNivel();
                    ronda++;
                    updateHitsFailsODS(true, juegoRetoAhorcado.getAhorcado().getId_ODS() , MainActivity.user);
                } else if (resultCode == RESULT_CANCELED) {
                    puntosTotales += juegoRetoAhorcado.getPuntos() * juegoRetoAhorcado.getNivel() * (-2);
                    if (puntosTotales < 0) {
                        puntosTotales = 0;
                    }
                    vidas--;
                    updateHitsFailsODS(false, juegoRetoAhorcado.getAhorcado().getId_ODS() , MainActivity.user);
                } else if (resultCode == RESULT_FIRST_USER) {
                    puntosTotales += juegoRetoAhorcado.getPuntos() * juegoRetoAhorcado.getNivel();
                    puntosConsolidados = puntosTotales;
                    haConsolidado = true;
                    ronda++;
                    updateHitsFailsODS(true, juegoRetoAhorcado.getAhorcado().getId_ODS() , MainActivity.user);
                }
                siguienteRetoAhorcado();
        }
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
