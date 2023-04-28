package Builder;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.Random;

import Domain_Layer.PartidaRetoDescubrirFrase;
import Persistence.AhorcadoRepository;
import Persistence.FraseRepository;

public class BuilderPartidaRetoDescubrirFrase extends BuilderPartida {
    public Thread hilo;
    protected PartidaRetoDescubrirFrase juego;

    public PartidaRetoDescubrirFrase getJuego() {return juego;}

    public BuilderPartidaRetoDescubrirFrase() { juego = new PartidaRetoDescubrirFrase(); }

    @Override
    public void buildNivel() {
        juego.setNivel(1);
    }

    @Override
    public void buildRetosNivel1() {
        hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    FraseRepository frasesBD = new FraseRepository(MainActivity.conexion);
                    juego.setFrasesNivel1(frasesBD.getListadoFrase());;
                    Collections.shuffle(juego.getFrasesNivel1(), new Random());
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
    public void buildRetosNivel2() {
        buildRetosNivel1();
    }

    @Override
    public void buildRetosNivel3() {
        buildRetosNivel1();
    }

    @Override
    public void buildTiempo() {
        juego.setTiempo(120000);
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
