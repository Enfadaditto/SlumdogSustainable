package Presentacion_layer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.List;

import Domain_Layer.Frase;

public class IUretoFrase_PruebaConSlider extends AppCompatActivity {
    LinearLayout letrasLayout;
    LinearLayout layoutFraseProblema;
    Frase frase;
    char[] fraseProblema;
    List<Character> listadoCaracteresFrase;
    TextView textoFraseProblema;
    DFTextView selectedTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase_con_horizontal_slider);

        frase = new Frase("Probando Frase", 0);

        letrasLayout = (LinearLayout) findViewById(R.id.letrasLayout);
        textoFraseProblema = (TextView) findViewById(R.id.TextoFraseProblema);
        layoutFraseProblema = (LinearLayout) findViewById(R.id.layoutFraseProblema);

        fraseProblema = frase.fraseProblema();
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);

        ponerFrasePorPantalla();
        rellenarLetrasLayout(listadoCaracteresFrase);
    }

    private void rellenarLetrasLayout(List<Character> listado)  {
        for (char letra : listado) {
            Button boton = new Button(this);
            boton.setLayoutParams(letrasLayout.getLayoutParams());
            boton.setText(letra+"");
            boton.setTextSize(48);
            boton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(comprobarFraseCorrecta((Button) v, selectedTextView)) {
                        letrasLayout.removeView(v);
                    }
                }
            });
            letrasLayout.addView(boton);
        }
    }

    private boolean comprobarFraseCorrecta(Button botonPulsado, DFTextView selectedTextView) {
        char letraPulsada = botonPulsado.getText().charAt(0);
        char letraSolucion = selectedTextView.getLetra();
        if (letraPulsada != letraSolucion) {
            textoFraseProblema.setText("Fallo");
            return false;
        }
        else {
            textoFraseProblema.setText("");
            selectedTextView.setText(letraSolucion+"");
            return true;
        }
    }

    private void ponerFrasePorPantalla() {
        //textoFraseProblema.setText(String.valueOf(fraseProblema));
        int index = 0;
        for (char letra : fraseProblema) {
            DFTextView letraTV = new DFTextView(this, index);
            letraTV.setLetra(frase.getFrase().toCharArray()[index]);
            letraTV.setLayoutParams(layoutFraseProblema.getLayoutParams());
            letraTV.setText(letra+"");
            letraTV.setTextSize(70);
            letraTV.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(((DFTextView) v).getText() != " ") selectedTextView = letraTV;
                }
            });
            layoutFraseProblema.addView(letraTV);
            index++;
        }
    }

}
