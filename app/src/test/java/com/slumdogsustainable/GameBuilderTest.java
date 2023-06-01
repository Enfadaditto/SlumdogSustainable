package com.slumdogsustainable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Domain_Layer.Patron_Builder.BuilderRetoPregunta;
import Domain_Layer.Patron_Builder.Director;
import Domain_Layer.RetoPregunta;

public class GameBuilderTest {
    @Test
    public void gameQuestionnotEmpty() {
        Director creadorDeJuego = new Director();
        BuilderRetoPregunta BuilderretoPregunta = new BuilderRetoPregunta();
        creadorDeJuego.setJuegoBuilder(BuilderretoPregunta);
        creadorDeJuego.construirJuego();
        RetoPregunta juegoRetoPregunta = BuilderretoPregunta.getJuego();
        assertTrue(!juegoRetoPregunta.getPreguntasNivel1().isEmpty() && !juegoRetoPregunta.getPreguntasNivel2().isEmpty() && !juegoRetoPregunta.getPreguntasNivel3().isEmpty());
    }

    @Test
    public void correctGameValues() {
        Director creadorDeJuego = new Director();
        BuilderRetoPregunta BuilderretoPregunta = new BuilderRetoPregunta();
        creadorDeJuego.setJuegoBuilder(BuilderretoPregunta);
        creadorDeJuego.construirJuego();
        RetoPregunta juegoRetoPregunta = BuilderretoPregunta.getJuego();
        assertEquals(1, juegoRetoPregunta.getNivel());
        assertEquals(100, juegoRetoPregunta.getPuntos());
        assertNotNull(juegoRetoPregunta.getTiempoOpcion());
        assertNotNull(juegoRetoPregunta.getTiempo());
    }
}
