package Domain_Layer;

import java.util.List;

public class PartidaRetoAhorcado extends Partida{

    private String palabra;
    private List<String> palabrasNivel1;
    private List<String> palabrasNivel2;
    private List<String> palabrasNivel3;

    public String getPalabra(){ return this.palabra; }
    public List<String> getPalabraNivel1() {
        return this.palabrasNivel1;
    }
    public List<String> getPalabraNivel2() {
        return this.palabrasNivel2;
    }
    public List<String> getPalabraNivel3() {
        return this.palabrasNivel3;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
    public void setPalabrasNivel1(List<String> palabrasNivel1) {
        this.palabrasNivel1 = palabrasNivel1;
    }
    public void setPalabrasNivel2(List<String> palabrasNivel2) {
        this.palabrasNivel2 = palabrasNivel2;
    }
    public void setPalabrasNivel3(List<String> palabrasNivel3) {
        this.palabrasNivel3 = palabrasNivel3;
    }
}

/*
- Se presenta la plantilla de la palabra o palabras con los huecos correspondientes a las letras y la separación de palabras.
        - Un máximo de 3 palabras y un mínimo de 1 palabra de 6 letras.
        - Se presentan todas las letras del alfabeto.
        - Cada vez que el participante selecciona una letra del alfabeto, si está en la palabra se descubren todas las ocurrencias de la letra. Si no está en la palabra, es un fallo y se dibuja un elemento del ahorcado en este orden: mástil vertical, mástil horizontal, cuerda, cabeza, tronco, brazo izq., brazo dcho., pierna izq., pierna dcha. y ejecución. Es decir el participante tiene 10 intentos.
        - La dificultad se establece con el número de elementos del ahorcado de inicio. Fácil: no hay ningún elemento dibujado (10 intentos), Medio: se parte con los mástiles y la cuerda dibujados (7 intentos), Difícil: se parte con los mástiles, la cuerda, cabeza y tronco dibujados (5 intentos).
        - También hay tiempo límite (2 minutos)
        - Tiene que haber una breve descripción que oriente al participante a modo de pista.
        - La puntuación, al igual que en el reto Pregunta, va asociada a la dificultad del reto.

 */