package Builder;

import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.Random;

import Domain_Layer.Question;
import Domain_Layer.RetoPregunta;
import Persistence.QuestionRepository;
import Persistence.SingletonConnection;

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
                    QuestionRepository preguntasEnBD = new QuestionRepository(SingletonConnection.getSingletonInstance());
                    juego.setPreguntasNivel1(Question.getQuestionListByDifficulty(preguntasEnBD, "Baja"));
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
                    QuestionRepository preguntasEnBD = new QuestionRepository(SingletonConnection.getSingletonInstance());
                    juego.setPreguntasNivel2(Question.getQuestionListByDifficulty(preguntasEnBD, "Media"));
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
                    QuestionRepository preguntasEnBD = new QuestionRepository(SingletonConnection.getSingletonInstance());
                    juego.setPreguntasNivel3(Question.getQuestionListByDifficulty(preguntasEnBD, "Alta"));
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
