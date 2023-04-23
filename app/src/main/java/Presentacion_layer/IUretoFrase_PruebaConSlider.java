package Presentacion_layer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.List;

import Domain_Layer.Frase;

public class IUretoFrase_PruebaConSlider extends AppCompatActivity {
    LinearLayout letrasLayout;

    Frase frase;
    char[] fraseProblema;
    List<Character> listadoCaracteresFrase;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase_con_horizontal_slider);

        frase = new Frase("Proba ndo Fra se", 0);

        letrasLayout = (LinearLayout) findViewById(R.id.letrasLayout);
        letrasLayout.removeAllViews();

        fraseProblema = frase.fraseProblema();
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);

        rellenarLetrasLayout(listadoCaracteresFrase);
    }

    private void rellenarLetrasLayout(List<Character> listado)  {
        for (char letra : listado) {
            Button boton = new Button(this);
            boton.setLayoutParams(letrasLayout.getLayoutParams());
            boton.setText(letra+"");
            boton.setTextSize(48);
            letrasLayout.addView(boton);
        }
    }

}
