package Builder;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.Random;

import Domain_Layer.Ahorcado;
import Domain_Layer.Partida;
import Domain_Layer.PartidaRetoAhorcado;
import Domain_Layer.PartidaRetoPregunta;
import Persistence.AhorcadoRepository;
import Persistence.QuestionRepository;

public class BuilderPartidaRetoAhorcado extends BuilderPartida{

    public Thread hilo;
    protected PartidaRetoAhorcado juego;

    public PartidaRetoAhorcado getJuego() {
        return juego;
    }

    public BuilderPartidaRetoAhorcado() {
        juego = new PartidaRetoAhorcado();
    }

    public void buildRetosNivel1() {
        hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    AhorcadoRepository preguntasEnBD = new AhorcadoRepository(MainActivity.conexion);
                    juego.setPalabrasNivel1(preguntasEnBD.getAhorcadoListByDifficulty("Baja"));
                    Collections.shuffle(juego.getPalabrasNivel1(), new Random());
                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        });
        hilo.start();
        try {
            hilo.join();
        } catch(Exception e) {}
    }

    public void buildRetosNivel2() {
        hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    AhorcadoRepository preguntasEnBD = new AhorcadoRepository(MainActivity.conexion);
                    juego.setPalabrasNivel2(preguntasEnBD.getAhorcadoListByDifficulty("Media"));
                    Collections.shuffle(juego.getPalabrasNivel2(), new Random());
                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        });
        hilo.start();
        try {
            hilo.join();
        } catch(Exception e) {}
    }

    public void buildRetosNivel3() {
        hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    AhorcadoRepository preguntasEnBD = new AhorcadoRepository(MainActivity.conexion);
                    juego.setPalabrasNivel3(preguntasEnBD.getAhorcadoListByDifficulty("Alta"));
                    Collections.shuffle(juego.getPalabrasNivel3(), new Random());
                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        });
        hilo.start();
        try {
            hilo.join();
        } catch(Exception e) {}
    }

    @Override
    public void buildNivel() {
        juego.setNivel(1);
    }

    @Override
    public void buildTiempo() {
        juego.setTiempo(120000); //10000ms = 10seg

    }

    @Override
    public void buildTiempoOpcion() {
        juego.setTiempoOpcion(15000);
    }

    @Override
    public void buildSonidoAcierto() {
        juego.setSonidoacierto(R.raw.correctanswer);
    }

    @Override
    public void buildSonidoFallo() {
        juego.setSonidofallo(R.raw.wronganswer);
    }

    @Override
    public void buildPuntos() {
        juego.setPuntos(5000 * juego.getNivel());
    }



}
