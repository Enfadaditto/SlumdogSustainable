package Patron_Fabrica;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

        System.out.println("CREANDO BOCADILLO DEL LOGRO: " + logro.getId_logro());
        this.context = context; this.logro = logro;
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new LayoutParams(0,0));
        GradientDrawable gradient = new GradientDrawable();
        gradient.setColor(0x94131313);
        gradient.setCornerRadius(20);
        this.setBackground(gradient);
    }

    public void crearImagen(){
        System.out.println("CREANDO IMAGEN DEL LOGRO: " + logro.getId_logro());
        if (logro.getTipo() == Logro.TipoLogro.Medalla) imagenLogro.setImageDrawable(getDrawable(context, R.drawable.logroconseguido));
        if (logro.getTipo() == Logro.TipoLogro.Trofeo) imagenLogro.setImageDrawable(getDrawable(context, R.drawable.trofeo_ganado));

        imagenLogro.setPadding(30,0,30,0);
        imagenLogro.setLayoutParams(new LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT,2));

    }
    public void crearSeparador() {
        System.out.println("CREANDO SEPARADORES DEL LOGRO: " + logro.getId_logro());
        barraVertical.setLayoutParams(new LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT));
        barraVertical.setBackgroundColor(Color.WHITE);
        barraHorizontal.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        barraHorizontal.setBackgroundColor(Color.WHITE);
    }
    public void crearLayoutTextos() {
        System.out.println("CREANDO ESPACIO DE TEXTOS DEL LOGRO: " + logro.getId_logro());
        textoLayout.setLayoutParams(new LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT,3));
        textoLayout.setOrientation(VERTICAL);
    }
    public void crearTextos() {
        System.out.println("AÑADIENDO TEXTOS DEL LOGRO: " + logro.getId_logro());
        nombreLogro.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        nombreLogro.setGravity(Gravity.CENTER|Gravity.LEFT);
        nombreLogro.setPadding(50,0,0,0);
        nombreLogro.setText(logro.getNombre()); nombreLogro.setTextColor(Color.WHITE);
        nombreLogro.setTextSize(18);

        descripcionLogro.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 101));
        descripcionLogro.setPadding(40,40,0,0);
        descripcionLogro.setText(logro.getDescripcion()); descripcionLogro.setTextColor(Color.WHITE);
        descripcionLogro.setTextSize(12);

    }
    public void addAlBocadillo() {
        System.out.println("MONTANDO EL BOCADILLO DEL LOGRO: " + logro.getId_logro());
        textoLayout.addView(nombreLogro);
        textoLayout.addView(barraHorizontal);
        textoLayout.addView(descripcionLogro);

        this.addView(imagenLogro);
        this.addView(barraVertical);
        this.addView(textoLayout);
    }
}
