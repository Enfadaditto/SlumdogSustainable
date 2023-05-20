package Patron_Fabrica;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slumdogsustainable.R;

import Domain_Layer.Logro;

public class BocadilloLogro extends LinearLayout implements IBocadilloLogro{
    private Context context;
    private Logro logro;
    private ImageView imagenLogro = new ImageView(this.getContext());
    private LinearLayout barraVertical = new LinearLayout(this.getContext()),
            textoLayout = new LinearLayout(this.getContext()),
            barraHorizontal = new LinearLayout(this.getContext());
    private TextView nombreLogro = new TextView(this.getContext()),
            descripcionLogro = new TextView(this.getContext());


    public BocadilloLogro(Context context, Logro logro) {
        super(context);
        this.context = context; this.logro = logro;
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new LayoutParams(0,0));
        this.setBackgroundColor(0x94131313);
    }

    public void crearImagen(){
        if (logro.getTipo() == Logro.TipoLogro.Medalla) imagenLogro.setImageDrawable(getDrawable(context, R.drawable.corazon_roto));
        if (logro.getTipo() == Logro.TipoLogro.Trofeo) imagenLogro.setImageDrawable(getDrawable(context, R.drawable.corazon_vida));

        imagenLogro.setLayoutParams(new LayoutParams(325, ViewGroup.LayoutParams.MATCH_PARENT,2));

    }
    public void crearSeparador() {
        barraVertical.setLayoutParams(new LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT));
        barraVertical.setBackgroundColor(Color.WHITE);
        barraHorizontal.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        barraHorizontal.setBackgroundColor(Color.WHITE);
    }
    public void crearLayoutTextos() {
        textoLayout.setLayoutParams(new LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT,3));
        textoLayout.setOrientation(VERTICAL);
    }
    public void crearTextos() {
        nombreLogro.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        nombreLogro.setGravity(Gravity.CENTER|Gravity.LEFT);
        nombreLogro.setPadding(10,0,0,0);
        nombreLogro.setText(logro.getNombre()); nombreLogro.setTextColor(Color.WHITE);

        descripcionLogro.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 101));
        descripcionLogro.setPadding(10,5,0,0);
        descripcionLogro.setText(logro.getDescripcion()); descripcionLogro.setTextColor(Color.WHITE);
        descripcionLogro.setTextSize(12);

    }
    public void addAlBocadillo() {
        textoLayout.addView(nombreLogro);
        textoLayout.addView(barraHorizontal);
        textoLayout.addView(descripcionLogro);

        this.addView(imagenLogro);
        this.addView(barraVertical);
        this.addView(textoLayout);
    }
}
