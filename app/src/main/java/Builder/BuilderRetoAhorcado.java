package Builder;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.Random;

import Domain_Layer.RetoAhorcado;
import Persistence.AhorcadoRepository;
import Persistence.SingletonConnection;

public class BuilderRetoAhorcado extends BuilderReto {

    public Thread hilo;
    protected RetoAhorcado juego;

    public RetoAhorcado getJuego() {
        return juego;
    }

    public BuilderRetoAhorcado() {
        juego = new RetoAhorcado();
    }

    public void buildRetosNivel1() {

        hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    AhorcadoRepository preguntasEnBD = new AhorcadoRepository(SingletonConnection.getSingletonInstance());
                    juego.setPalabras(preguntasEnBD.obtenerTodos());
                    Collections.shuffle(juego.getPalabras(), new Random());
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



    public void buildRetosNivel2() {buildRetosNivel1();}

    public void buildRetosNivel3() {buildRetosNivel1();}

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
        juego.setPuntos(100 * juego.getNivel());
    }



}
