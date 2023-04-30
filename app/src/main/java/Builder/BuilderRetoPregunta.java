package Builder;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.Random;

import Domain_Layer.RetoPregunta;
import Persistence.QuestionRepository;

public class BuilderRetoPregunta extends BuilderReto {

    public Thread hilo;

    protected RetoPregunta juego;

    public RetoPregunta getJuego() {
        return juego;
    }

    public BuilderRetoPregunta() {
        juego = new RetoPregunta();
    }

    public void buildRetosNivel1() {
        hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    QuestionRepository preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    juego.setPreguntasNivel1(preguntasEnBD.getQuestionListByDifficulty("Baja"));
                    Collections.shuffle(juego.getPreguntasNivel1(), new Random());
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
                    QuestionRepository preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    juego.setPreguntasNivel2(preguntasEnBD.getQuestionListByDifficulty("Media"));
                    Collections.shuffle(juego.getPreguntasNivel2(), new Random());
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
                    QuestionRepository preguntasEnBD = new QuestionRepository(MainActivity.conexion);
                    juego.setPreguntasNivel3(preguntasEnBD.getQuestionListByDifficulty("Alta"));
                    Collections.shuffle(juego.getPreguntasNivel3(), new Random());
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
        juego.setTiempo(30000); //10000ms = 10seg
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
        juego.setPuntos(100 * juego.getNivel());
    }


}
