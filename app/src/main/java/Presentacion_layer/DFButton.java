package Presentacion_layer;

import android.content.Context;
import android.widget.Button;

public class DFButton extends androidx.appcompat.widget.AppCompatButton {
    private char letra;

    public DFButton(Context context) {super(context); }
    public DFButton(Button boton, char letra) {
        super(boton.getContext());
        this.letra = letra;
    }

    public void setLetra(char letra) { this.letra = letra; }
    public char getLetra() { return letra; }
}
