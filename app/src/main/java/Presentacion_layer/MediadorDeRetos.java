package Presentacion_layer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import Builder.BuilderRetoAhorcado;
import Builder.BuilderRetoDescubrirFrase;
import Builder.BuilderRetoPregunta;
import Builder.Director;
import Domain_Layer.Reto;
import Domain_Layer.RetoAhorcado;
import Domain_Layer.RetoDescubrirFrase;
import Domain_Layer.RetoPregunta;
import Domain_Layer.User;
import Persistence.ODS_URepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class MediadorDeRetos extends AppCompatActivity implements MediatorInterface{
    int vidas = 1, ronda = 1, indiceRetoFacil = 0, indiceRetoDificil = 0, indiceRetoMedio = 0, puntosTotales, puntosConsolidados, erroresRetoAhorcado;
    RetoPregunta juegoRetoPregunta;
    RetoAhorcado juegoRetoAhorcado;
    int pistas =3;

    RetoDescubrirFrase juegoRetoDescubrirFrase;
    Button botonRetoPregunta, botonRetoAhorcado, botonRetoFrase, botonRetoMixto;
    boolean retoPreguntaEscogido, retoAhorcadoEscogido, retoDescubrirFraseEscogido, retoMixtoEscodigo, haConsolidado = false;
    public final static int REQUESTCODE = 100;

    public final static int ABANDON = 100;
    BuilderRetoPregunta retoPregunta;
    BuilderRetoAhorcado retoAhorcado;
    BuilderRetoDescubrirFrase retoDescubrirFrase;


    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        botonRetoAhorcado = findViewById(R.id.botonRetoAhorcado);
        botonRetoPregunta = findViewById(R.id.botonRetoPregunta);
        botonRetoFrase = findViewById(R.id.botonRetoFrase);
        botonRetoMixto = findViewById(R.id.botonRetoMixto);
        Bundle extras = getIntent().getExtras();
        String tipoReto = extras.getString("tipoReto");

        switch (tipoReto) {
            case "RetoPregunta":
                retoPreguntaEscogido = true;
                break;
            case "RetoAhorcado":
                retoAhorcadoEscogido = true;
                break;
            case "RetoFrase":
                retoDescubrirFraseEscogido = true;
                break;
        }
        empezarPartida();
    }

    public void empezarPartida() {
        MainActivity.music.stop();
        MainActivity.background.start();
        if (retoAhorcadoEscogido) {
            retoAhorcado = new BuilderRetoAhorcado();
            Director creadorDeJuego = new Director();
            creadorDeJuego.setJuegoBuilder(retoAhorcado);
            creadorDeJuego.construirJuego();
            juegoRetoAhorcado = retoAhorcado.getJuego();
            siguienteRetoAhorcado();
        } else if (retoPreguntaEscogido) {
            retoPregunta = new BuilderRetoPregunta();
            Director creadorDeJuego = new Director();
            creadorDeJuego.setJuegoBuilder(retoPregunta);
            creadorDeJuego.construirJuego();
            juegoRetoPregunta = retoPregunta.getJuego();
            siguienteRetoPregunta();
        } else if (retoDescubrirFraseEscogido) {
            retoDescubrirFrase = new BuilderRetoDescubrirFrase();
            Director creadorDeJuego = new Director();
            creadorDeJuego.setJuegoBuilder(retoDescubrirFrase);
            creadorDeJuego.construirJuego();
            juegoRetoDescubrirFrase = retoDescubrirFrase.getJuego();
            siguienteRetoDescubrirFrase();
        }
    }

    public void updateGamesandTime(Boolean won, int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                u.updateGamesandTime(won, time);
            }
        }).start();
    }

    public void updateGamesAbandonedandTime(int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                u.updateGamesAbandonedandTime(time);
            }
        }).start();
    }

    public void siguienteRetoPregunta() {
        if (ronda > 10) {
            updateGamesandTime(true, juegoRetoPregunta.getTiempo() * 10);
            finish();
            return;
        }
        if (vidas < 0) {
            updateGamesandTime(false, juegoRetoPregunta.getTiempo() * ronda);
            finish();
            return;
        } else if (ronda <= 4) {
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel1().get(indiceRetoFacil++));
        } else if (ronda > 4 && ronda <= 7) {
            juegoRetoPregunta.setNivel(2);
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel2().get(indiceRetoMedio++));
        } else {
            juegoRetoPregunta.setNivel(3);
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel3().get(indiceRetoDificil++));
        }
        juegoRetoPregunta.setIdOds(juegoRetoPregunta.getPreguntaActual().getOds());
        iniciarDatosRetoPregunta();
    }

    public void siguienteRetoAhorcado() {
        if (ronda > 10) {
            updateGamesandTime(true, juegoRetoAhorcado.getTiempo() * 10);
            finish();
            return;
        }
        if (vidas < 0) {
            updateGamesandTime(false, juegoRetoAhorcado.getTiempo() * ronda);
            finish();
            return;
        }
        else if(ronda <= 4){

            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getAhorcado());
            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabras().get(indiceRetoFacil++));
            juegoRetoAhorcado.setErroresRetoAhorcado(0);
        }

        else if(ronda > 4 && ronda <= 7) {

            juegoRetoAhorcado.setNivel(2);
            juegoRetoAhorcado.setErroresRetoAhorcado(3);
            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabras().get(indiceRetoMedio++));
            //juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabrasNivel2().get());
        }

        else {

            juegoRetoAhorcado.setNivel(3);
            juegoRetoAhorcado.setErroresRetoAhorcado(5);
            juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabras().get(indiceRetoDificil++));
            //juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabrasNivel3().get(indicePreguntasDificil++));
        }
        juegoRetoAhorcado.setIdOds(juegoRetoAhorcado.getAhorcado().getId_ODS());
        iniciarDatosRetoAhorcado();
    }

    public void siguienteRetoDescubrirFrase() {

        if (ronda > 10) {
            updateGamesandTime(true, juegoRetoAhorcado.getTiempo() * 10);
            finish();
            return;
        }
        if (vidas < 0) {
            updateGamesandTime(false, juegoRetoAhorcado.getTiempo() * ronda);
            finish();
            return;
        } else if (ronda <= 4) {
            juegoRetoDescubrirFrase.setNivel(1);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indiceRetoFacil++));
        } else if (ronda > 4 && ronda <= 7) {
            juegoRetoDescubrirFrase.setNivel(2);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indiceRetoFacil++));
        } else {
            juegoRetoDescubrirFrase.setNivel(3);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indiceRetoFacil++));
        }
        juegoRetoDescubrirFrase.setIdOds(juegoRetoDescubrirFrase.getFraseActual().getIdOds());
        iniciarDatosRetoDescubrirFrase();
    }

    public void iniciarDatosRetoAhorcado() {
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
        b.putInt("Pistas", pistas);
        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }



    public void iniciarDatosRetoPregunta() {
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
        b.putInt("Pistas", pistas);

        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }

    public void iniciarDatosRetoDescubrirFrase() {
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
        b.putInt("odsFrase", juegoRetoDescubrirFrase.getFraseActual().getId_ODS());
        b.putInt("Pistas", pistas);
        I.putExtras(b);
        startActivityForResult(I, REQUESTCODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (retoPreguntaEscogido) {
            handleActivityResult(juegoRetoPregunta, requestCode, resultCode);
            siguienteRetoPregunta();
        } else if (retoAhorcadoEscogido) {
            handleActivityResult(juegoRetoAhorcado, requestCode, resultCode);
            siguienteRetoAhorcado();
        } else if (retoDescubrirFraseEscogido) {
            handleActivityResult(juegoRetoDescubrirFrase, requestCode, resultCode);
            siguienteRetoDescubrirFrase();
        }
    }

    private void handleActivityResult(Reto juegoReto, int requestCode, int resultCode) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == ABANDON) {
                updateGamesAbandonedandTime(juegoReto.getTiempo() * ronda);
                finish();
            } else if (resultCode == RESULT_OK) {
                puntosTotales += juegoReto.getPuntos() * juegoReto.getNivel();
                ronda++;
                updateHitsFailsODS(true, juegoReto.getIdOds(), MainActivity.user);
            } else if (resultCode == RESULT_CANCELED) {
                puntosTotales += juegoReto.getPuntos() * juegoReto.getNivel() * (-2);
                if (puntosTotales < 0) {
                    puntosTotales = 0;
                }
                vidas--;
                updateHitsFailsODS(false, juegoReto.getIdOds(), MainActivity.user);
            } else if (resultCode == RESULT_FIRST_USER) {
                puntosTotales += juegoReto.getPuntos() * juegoReto.getNivel();
                puntosConsolidados = puntosTotales;
                haConsolidado = true;
                ronda++;
                updateHitsFailsODS(true, juegoReto.getIdOds(), MainActivity.user);
            }
        }
    }

    public void updateHitsFailsODS(boolean hit, int idODS, User u) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ODS_URepository ODS = new ODS_URepository(SingletonConnection.getSingletonInstance());
                ODS.updateODS(hit, idODS, u);
            }
        }).start();
    }
}
