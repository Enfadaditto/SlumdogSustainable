package Presentacion_layer;

import android.content.Context;
import android.widget.TextView;

public class DFTextView extends androidx.appcompat.widget.AppCompatTextView {
    private int indice;
    private char letra;
    public DFTextView(Context context) {
        super(context);
    }

    public DFTextView(Context context, int indice) {
        this(context);
        this.indice = indice;
    }

    public int getIndice() { return indice; }
    public void setLetra(char letra) { this.letra = letra; }
    public char getLetra() { return letra; }
}
