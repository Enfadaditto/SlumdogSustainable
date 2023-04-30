package Domain_Layer;

import java.util.List;

public class RetoDescubrirFrase extends Reto {

    private Frase fraseEnunciado;

    private List<Frase> frasesNivel1;

    private List<Frase> frasesNivel2;

    private List<Frase> frasesNivel3;

    public Frase getFraseActual() {
        return fraseEnunciado;
    }

    public void setFraseEnunciado(Frase fraseEnunciado) { this.fraseEnunciado = fraseEnunciado; }

    public List<Frase> getFrasesNivel1() { return frasesNivel1; }

    public void setFrasesNivel1(List<Frase> frasesNivel1) { this.frasesNivel1 = frasesNivel1; }

    public List<Frase> getFrasesNivel2() { return frasesNivel2; }

    public void setFrasesNivel2(List<Frase> frasesNivel2) { this.frasesNivel2 = frasesNivel2; }

    public List<Frase> getFrasesNivel3() { return frasesNivel3; }

    public void setFrasesNivel3(List<Frase> frasesNivel3) { this.frasesNivel3 = frasesNivel3; }
}
