package Domain_Layer;

import android.content.Context;
import android.widget.Button;

public class DFButton extends androidx.appcompat.widget.AppCompatButton {
    private char letra;
    private Button boton;

    public DFButton(Context context) {super(context); }
    public DFButton(Button boton, char letra) {
        super(boton.getContext());
        this.boton = boton;
        this.letra = letra;
    }

    public void setLetra(char letra) { this.letra = letra; }
    public char getLetra() { return letra; }

    public Button getBoton() { return this.boton; }
}
