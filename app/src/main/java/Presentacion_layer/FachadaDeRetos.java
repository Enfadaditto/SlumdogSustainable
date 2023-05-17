package Presentacion_layer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.List;
import java.util.Random;

import Builder.BuilderRetoAhorcado;
import Builder.BuilderRetoDescubrirFrase;
import Builder.BuilderRetoPregunta;
import Builder.Director;
import Domain_Layer.Logro;
import Domain_Layer.Reto;
import Domain_Layer.RetoAhorcado;
import Domain_Layer.RetoDescubrirFrase;
import Domain_Layer.RetoPregunta;
import Domain_Layer.User;
import Domain_Layer.User_has_Logro;
import Persistence.LogroRepository;
import Persistence.ODS_URepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class FachadaDeRetos extends AppCompatActivity implements FachadaInterface {
    int vidas = 1, ronda = 1, retoRandom, indiceRetoFacil = 0, indiceRetoDificil = 0, indiceRetoMedio = 0, puntosTotales, puntosConsolidados, erroresRetoAhorcado;
    RetoPregunta juegoRetoPregunta;
    RetoAhorcado juegoRetoAhorcado;
    public static int pistas = 3;
    public static Boolean  haUsadoPista= false;

    public static int partidasEnRacha = 0;
    RetoDescubrirFrase juegoRetoDescubrirFrase;
    Button botonRetoPregunta, botonRetoAhorcado, botonRetoFrase, botonRetoMixto;
    boolean retoPreguntaEscogido, retoAhorcadoEscogido, retoDescubrirFraseEscogido, retoMixtoEscogido, haConsolidado = false;
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
        addObservadores(MainActivity.user);

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
            case "RetoMixto":
                retoMixtoEscogido = true;
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
        }
        else if (retoPreguntaEscogido) {
            retoPregunta = new BuilderRetoPregunta();
            Director creadorDeJuego = new Director();
            creadorDeJuego.setJuegoBuilder(retoPregunta);
            creadorDeJuego.construirJuego();
            juegoRetoPregunta = retoPregunta.getJuego();
            siguienteRetoPregunta();
        }
        else if (retoDescubrirFraseEscogido) {
            retoDescubrirFrase = new BuilderRetoDescubrirFrase();
            Director creadorDeJuego = new Director();
            creadorDeJuego.setJuegoBuilder(retoDescubrirFrase);
            creadorDeJuego.construirJuego();
            juegoRetoDescubrirFrase = retoDescubrirFrase.getJuego();
            siguienteRetoDescubrirFrase();
        }
        else if (retoMixtoEscogido) {
            retoDescubrirFrase = new BuilderRetoDescubrirFrase();
            Director creadorDeJuego = new Director();
            creadorDeJuego.setJuegoBuilder(retoDescubrirFrase);
            creadorDeJuego.construirJuego();
            juegoRetoDescubrirFrase = retoDescubrirFrase.getJuego();
            retoPregunta = new BuilderRetoPregunta();
            creadorDeJuego.setJuegoBuilder(retoPregunta);
            creadorDeJuego.construirJuego();
            juegoRetoPregunta = retoPregunta.getJuego();
            retoAhorcado = new BuilderRetoAhorcado();
            creadorDeJuego.setJuegoBuilder(retoAhorcado);
            creadorDeJuego.construirJuego();
            juegoRetoAhorcado = retoAhorcado.getJuego();
            siguienteRetoMixto();
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

    public void siguienteRetoMixto() {
        Random r = new Random();
        retoRandom = r.nextInt(3);
        switch (retoRandom) {
            case 0:
                siguienteRetoPregunta();
                break;
            case 1:
                siguienteRetoAhorcado();
                break;
            case 2:
                siguienteRetoDescubrirFrase();
                break;
        }

    }
    public void siguienteRetoPregunta() {
        if (ronda > 10) {
            updateGamesandTime(true, juegoRetoPregunta.getTiempo() * 10);
            finish();
            pistas = 3;
            partidasEnRacha++;
            return;
        }
        if (vidas < 0) {
            updateGamesandTime(false, juegoRetoPregunta.getTiempo() * ronda);
            finish();
            pistas = 3;
            partidasEnRacha = 0;
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
        crearRetoPregunta();
    }

    public void siguienteRetoAhorcado() {
        if (ronda > 10) {
            updateGamesandTime(true, juegoRetoAhorcado.getTiempo() * 10);
            finish();
            pistas = 3;
            partidasEnRacha++;
            return;
        }
        if (vidas < 0) {
            updateGamesandTime(false, juegoRetoAhorcado.getTiempo() * ronda);
            finish();
            pistas = 3;
            partidasEnRacha = 0;
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
        crearRetoAhorcado();
    }

    public void siguienteRetoDescubrirFrase() {

        if (ronda > 10) {
            updateGamesandTime(true, juegoRetoAhorcado.getTiempo() * 10);
            finish();
            pistas = 3;
            partidasEnRacha++;
            return;
        }
        if (vidas < 0) {
            updateGamesandTime(false, juegoRetoAhorcado.getTiempo() * ronda);
            finish();
            pistas = 3;
            partidasEnRacha = 0;
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
        crearRetoDescubrirFrase();
    }

    public void crearRetoAhorcado() {
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

    public void crearRetoPregunta() {
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

    public void crearRetoDescubrirFrase() {
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
        comprobarLogros();

        if (retoPreguntaEscogido) {
            handleActivityResult(juegoRetoPregunta, requestCode, resultCode);
            if(!this.isFinishing()) {
                siguienteRetoPregunta();
            }
        } else if (retoAhorcadoEscogido) {
            handleActivityResult(juegoRetoAhorcado, requestCode, resultCode);
            if(!this.isFinishing()) {
                siguienteRetoAhorcado();
            }
        } else if (retoDescubrirFraseEscogido) {
            handleActivityResult(juegoRetoDescubrirFrase, requestCode, resultCode);
            if(!this.isFinishing()) {
                siguienteRetoDescubrirFrase();
            }
        }
        else if(retoMixtoEscogido) {
            switch (retoRandom) {
                case 0:
                    handleActivityResult(juegoRetoPregunta, requestCode, resultCode);
                    break;
                case 1:
                    handleActivityResult(juegoRetoAhorcado, requestCode, resultCode);
                    break;
                case 2:
                    handleActivityResult(juegoRetoDescubrirFrase, requestCode, resultCode);
                    break;
            }
            if(!this.isFinishing()) {
                siguienteRetoMixto();
            }
        }
    }

    private void handleActivityResult(Reto juegoReto, int requestCode, int resultCode) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == ABANDON) {
                updateGamesAbandonedandTime(juegoReto.getTiempo() * ronda);
                finish();
            } else if (resultCode == RESULT_OK) {
                if(haUsadoPista){
                    puntosTotales += (juegoReto.getPuntos() * juegoReto.getNivel())/2;
                    haUsadoPista = false;
                }else {
                    puntosTotales += juegoReto.getPuntos() * juegoReto.getNivel();
                }
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
                if(haUsadoPista){
                    puntosTotales += (juegoReto.getPuntos() * juegoReto.getNivel())/2;
                    haUsadoPista = false;
                }else {
                    puntosTotales += juegoReto.getPuntos() * juegoReto.getNivel();
                }
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

    public void comprobarLogros() {
        User usuario = MainActivity.user;

        if (usuario.getTimeSpent() == 0) notificarYEliminarObservador(usuario, 1);
        if (usuario.getGamesAchieved() == 1) notificarYEliminarObservador(usuario, 2);
        if (ronda > 10 && pistas > 0) notificarYEliminarObservador(usuario, 3);
        if (ronda > 10 && pistas == 3) notificarYEliminarObservador(usuario, 4);
        //del 5 - 22 son cobertura ODS
        if (partidasEnRacha == 3) notificarYEliminarObservador(usuario, 23);
        if (usuario.getPointsAchieved() >= 10000) notificarYEliminarObservador(usuario, 24);
        if (usuario.getPointsAchieved() >= 100000) notificarYEliminarObservador(usuario, 25);
        if (usuario.getPointsAchieved() >= 1000000) notificarYEliminarObservador(usuario, 26);
        if (ronda > 10 && retoMixtoEscogido) notificarYEliminarObservador(usuario, 27);
        if (ronda == 1 && vidas == 0) notificarYEliminarObservador(usuario, 30);
    }

    public void notificarYEliminarObservador(User u, int id_logro) {
        User_has_Logro l = new User_has_Logro("", -1);
        u.notificarObservadores(id_logro);
        l.setCompletado(true);
        l = l.getLogroPorID(id_logro);
        u.eliminarObservador(l);
    }

    public void addObservadores(User u) {
        User_has_Logro l = new User_has_Logro("",-1);
        List<User_has_Logro> logros = l.getAllLogros(u);

        for (User_has_Logro x : logros) {
            if (!x.isCompletado())
                u.agregarObservador(x);
        }
    }

    int contadorClick = 0;
    public void easterEggOnClick(View v) {
        contadorClick++;
        if (contadorClick == 5) notificarYEliminarObservador(MainActivity.user, 29);
    }
}
