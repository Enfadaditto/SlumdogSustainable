package Domain_Layer;

import java.util.List;

public class PartidaRetoPregunta extends Partida {
    private Question preguntaActual;

    private List<Question> preguntasNivel1;

    private List<Question> preguntasNivel2;

    private List<Question> preguntasNivel3;

    public Question getPreguntaActual() {
        return this.preguntaActual;
    }

    public List<Question> getPreguntasNivel1() {
        return this.preguntasNivel1;
    }

    public List<Question> getPreguntasNivel2() {
        return this.preguntasNivel2;
    }

    public List<Question> getPreguntasNivel3() {
        return this.preguntasNivel3;
    }

    public void setPreguntaActual(Question q) {
        this.preguntaActual = q;
    }

    public void setPreguntasNivel1(List<Question> ql) {
        this.preguntasNivel1 = ql;
    }

    public void setPreguntasNivel2(List<Question> ql) {
        this.preguntasNivel2 = ql;
    }

    public void setPreguntasNivel3(List<Question> ql) {
        this.preguntasNivel3 = ql;
    }
}
