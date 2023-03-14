package Builder;

public class CreadorDeJuego {

    private JuegoBuilder juegoBuilder;


        juegoBuilder = jb;

        return juegoBuilder.getJuego();
    }


        juegoBuilder.buildDificultad();
        juegoBuilder.buildNivel();
        juegoBuilder.buildPuntos();
        juegoBuilder.buildSonidoAcierto();
        juegoBuilder.buildSonidoFallo();
        juegoBuilder.buildTiempo();
    }

}
