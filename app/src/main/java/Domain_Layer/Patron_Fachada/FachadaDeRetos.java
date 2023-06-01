package Domain_Layer.Patron_Fachada;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.Random;

import Domain_Layer.Patron_Builder.BuilderRetoAhorcado;
import Domain_Layer.Patron_Builder.BuilderRetoDescubrirFrase;
import Domain_Layer.Patron_Builder.BuilderRetoPregunta;
import Domain_Layer.Logro;
import Domain_Layer.Reto;
import Domain_Layer.RetoAhorcado;
import Domain_Layer.RetoDescubrirFrase;
import Domain_Layer.RetoPregunta;
import Domain_Layer.User;
import Domain_Layer.Patron_strategy.Context;
import Domain_Layer.Patron_strategy.AhorcadoStrategy;
import Domain_Layer.Patron_strategy.FraseStrategy;
import Domain_Layer.Patron_strategy.MixtoStrategy;
import Domain_Layer.Patron_strategy.PreguntaStrategy;
import Persistence.ODS_URepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
import Presentacion_layer.IUretoAhorcado;
import Presentacion_layer.IUretoFrase;
import Presentacion_layer.IUretoPregunta;

public class FachadaDeRetos extends AppCompatActivity implements FachadaInterface {
    public static int vidas = 1, ronda = 1, retoRandom, indiceRetoFacil = 0, indiceRetoDificil = 0, indiceRetoMedio = 0, puntosTotales, puntosConsolidados, erroresRetoAhorcado;
    public static RetoPregunta juegoRetoPregunta = null;
    public static RetoAhorcado juegoRetoAhorcado = null;
    public static int pistas = 3;
    public static Boolean  haUsadoPista= false;

    public static int partidasEnRacha = 0;
    public static RetoDescubrirFrase juegoRetoDescubrirFrase = null;
    Button botonRetoPregunta, botonRetoAhorcado, botonRetoFrase, botonRetoMixto;
    public static boolean retoPreguntaEscogido, retoAhorcadoEscogido, retoDescubrirFraseEscogido, retoMixtoEscogido, haConsolidado = false;
    public final static int REQUESTCODE = 100;

    public final static int ABANDON = 100;
    public static BuilderRetoPregunta retoPregunta;
    public static BuilderRetoAhorcado retoAhorcado;
    public static BuilderRetoDescubrirFrase retoDescubrirFrase;

    public static boolean easterEgg = false;

    Context context;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = new Context();
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
            case "RetoMixto":
                retoMixtoEscogido = true;
                break;
        }
        empezarPartida();
    }

    public void empezarPartida() {
        MainActivity.music.stop();
        MainActivity.background = MediaPlayer.create(getApplicationContext(), R.raw.backgroundmusic);
        MainActivity.background.setLooping(true);
        MainActivity.background.start();

        if (retoAhorcadoEscogido) {
            context.setEstrategia(new AhorcadoStrategy());
            context.obtenerRetos();
            siguienteRetoAhorcado();
        } else if (retoPreguntaEscogido) {
            context.setEstrategia(new PreguntaStrategy());
            context.obtenerRetos();
            siguienteRetoPregunta();
        } else if (retoDescubrirFraseEscogido) {
            context.setEstrategia(new FraseStrategy());
            context.obtenerRetos();
            siguienteRetoDescubrirFrase();
        } else if (retoMixtoEscogido) {
            context.setEstrategia(new MixtoStrategy());
            context.obtenerRetos();
            siguienteRetoMixto();
        }
    }


    public void updatePartidasandTime(Boolean hit, Boolean abandonada, int time, int Puntos) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                User.updatePartidasandTime(u, hit, abandonada, time, Puntos);
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
    private void updateGameStateAndFinish(boolean ganado, int tiempo, int puntos) {
        updatePartidasandTime(ganado, false, tiempo, puntos);
        setResult(100);
        finish();
        pistas = 3;
        partidasEnRacha = ganado ? partidasEnRacha + 1 : 0;

        new Thread(() -> {
            comprobarLogros();
        }).start();
    }

    private void setPreguntaActual() {
        if (ronda <= 4) {
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel1().get(indiceRetoFacil++));
        } else if (ronda > 4 && ronda <= 7) {
            juegoRetoPregunta.setNivel(2);
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel2().get(indiceRetoMedio++));
        } else {
            juegoRetoPregunta.setNivel(3);
            juegoRetoPregunta.setPreguntaActual(juegoRetoPregunta.getPreguntasNivel3().get(indiceRetoDificil++));
        }
    }

    private void setAhorcadoActual() {
        if (ronda <= 4) {
            juegoRetoAhorcado.setErroresRetoAhorcado(0);
        } else if (ronda > 4 && ronda <= 7) {
            juegoRetoAhorcado.setNivel(2);
            juegoRetoAhorcado.setErroresRetoAhorcado(3);
        } else {
            juegoRetoAhorcado.setNivel(3);
            juegoRetoAhorcado.setErroresRetoAhorcado(5);
        }
        juegoRetoAhorcado.setAhorcado(juegoRetoAhorcado.getPalabras().get(indiceRetoFacil++));
    }

    private void setDescubrirFraseActual() {
        if (ronda <= 4) {
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel1().get(indiceRetoFacil++));
        } else if (ronda > 4 && ronda <= 7) {
            juegoRetoDescubrirFrase.setNivel(2);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel2().get(indiceRetoMedio++));
        } else {
            juegoRetoDescubrirFrase.setNivel(3);
            juegoRetoDescubrirFrase.setFraseEnunciado(juegoRetoDescubrirFrase.getFrasesNivel3().get(indiceRetoDificil++));
        }
    }
    public void siguienteRetoPregunta() {
        if (ronda > 10) {
            updateGameStateAndFinish(true, juegoRetoPregunta.getTiempo() * 10, puntosTotales);
            return;
        }
        if (vidas < 0) {
            updateGameStateAndFinish(false, juegoRetoPregunta.getTiempo() * ronda, 0);
            return;
        }

        setPreguntaActual();
        juegoRetoPregunta.setIdOds(juegoRetoPregunta.getPreguntaActual().getOds());
        context.setEstrategia(new PreguntaStrategy());
        startGame(IUretoPregunta.class, context.crearReto());
    }

    public void siguienteRetoAhorcado() {
        if (ronda > 10) {
            updateGameStateAndFinish(true, juegoRetoAhorcado.getTiempo() * 10, puntosTotales);
            return;
        }
        if (vidas < 0) {
            updateGameStateAndFinish(false, juegoRetoAhorcado.getTiempo() * ronda, 0);
            return;
        }

        setAhorcadoActual();
        juegoRetoAhorcado.setIdOds(juegoRetoAhorcado.getAhorcado().getId_ODS());
        context.setEstrategia(new AhorcadoStrategy());
        startGame(IUretoAhorcado.class, context.crearReto());
    }

    public void siguienteRetoDescubrirFrase() {
        if (ronda > 10) {
            updateGameStateAndFinish(true, juegoRetoDescubrirFrase.getTiempo() * 10, puntosTotales);
            return;
        }
        if (vidas < 0) {
            updateGameStateAndFinish(false, juegoRetoDescubrirFrase.getTiempo() * ronda, 0);
            return;
        }

        setDescubrirFraseActual();
        juegoRetoDescubrirFrase.setIdOds(juegoRetoDescubrirFrase.getFraseActual().getId_ODS());
        context.setEstrategia(new FraseStrategy());
        startGame(IUretoFrase.class, context.crearReto());
    }

    private void startGame(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUESTCODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                updatePartidasandTime(false, true, juegoReto.getTiempo() * ronda, puntosConsolidados);
                //updateGamesAbandonedandTime(juegoReto.getTiempo() * ronda);
                new Thread(() -> {
                    comprobarLogros();
                }).start();
                setResult(100);

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
                if (juegoReto instanceof RetoAhorcado) indiceRetoFacil--;
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
                u.updateODS(hit, idODS, ODS);
            }
        }).start();
    }

    public void comprobarLogros() {
        User usuario = MainActivity.user;

        if (usuario.getTimeSpent() == 0) usuario.desbloquearLogro(new Logro().getLogroPorID(1));
        if (usuario.getGamesAchieved() == 1) usuario.desbloquearLogro(new Logro().getLogroPorID(2));
        if (ronda > 10 && pistas > 0) usuario.desbloquearLogro(new Logro().getLogroPorID(3));
        if (ronda > 10 && pistas == 3) usuario.desbloquearLogro(new Logro().getLogroPorID(4));
        if (partidasEnRacha == 3) usuario.desbloquearLogro(new Logro().getLogroPorID(23));
        if (usuario.getPointsAchieved() >= 10000) usuario.desbloquearLogro(new Logro().getLogroPorID(24));
        if (usuario.getPointsAchieved() >= 100000) usuario.desbloquearLogro(new Logro().getLogroPorID(25));
        if (usuario.getPointsAchieved() >= 1000000) usuario.desbloquearLogro(new Logro().getLogroPorID(26));
        if (ronda > 10 && retoMixtoEscogido) usuario.desbloquearLogro(new Logro().getLogroPorID(27));
        if (usuario.getGamesAchieved() == 20) usuario.desbloquearLogro(new Logro().getLogroPorID(28));
        if (easterEgg) usuario.desbloquearLogro(new Logro().getLogroPorID(29));
        if (ronda == 1 && vidas < 0) usuario.desbloquearLogro(new Logro().getLogroPorID(30));
    }

    public void onDestroy () {
        super.onDestroy();
        MainActivity.music.stop();
        MainActivity.background.stop();
        retoAhorcadoEscogido = false;
        retoDescubrirFraseEscogido = false;
        retoMixtoEscogido = false;
        retoPreguntaEscogido = false;
        haConsolidado = false;
        pistas = 3;
        puntosTotales = 0;
        puntosConsolidados = 0;
        indiceRetoFacil = 0;
        indiceRetoMedio = 0;
        indiceRetoDificil = 0;
        ronda = 1;
        vidas = 1;
    }



}
