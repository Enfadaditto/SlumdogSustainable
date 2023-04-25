package Presentacion_layer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SyncAdapterType;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.util.Collections;
import java.util.List;

import Domain_Layer.Frase;

public class IUretoFrase extends AppCompatActivity {
    LinearLayout letrasLayout;
    RelativeLayout panelDescubrirFrase;
    Frase frase;
    char[] fraseProblema;
    List<Character> listadoCaracteresFrase;

    //MODIFICAR ESTO
    Button casillaElegida;
    DFButton casillaElegidaSolucion;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase);


        // SE VA A CAMBIAR LA IDEA PARA AÑADIR LA FRASE PROBLEMA EN UN PANEL DE MUCHOS BOTONES

        frase = new Frase("Probando frase, (ODS 14)", 0, "Facil");

        letrasLayout = (LinearLayout) findViewById(R.id.letrasLayout);
        panelDescubrirFrase = (RelativeLayout) findViewById(R.id.panelDescubrirFrase);

        fraseProblema = frase.getFraseProblema();
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);
        casillaElegida = new DFButton(this);

        GuideAlert();

        rellenarLetrasLayout(listadoCaracteresFrase);
    }

    private void rellenarLetrasLayout(List<Character> listado)  {
        for (char letra : listado) {
            Button boton = new Button(this);
            boton.setLayoutParams(letrasLayout.getLayoutParams());
            boton.setBackground(getDrawable(R.drawable.botones_teclado));
            boton.setText(letra+""); boton.setTextColor(Color.WHITE);
            boton.setTextSize(48);
            boton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(comprobarFraseCorrecta((Button) v, casillaElegida, casillaElegidaSolucion)) {
                        letrasLayout.removeView(v);
                    }
                }
            });
            letrasLayout.addView(boton);
            TextView espacio = new TextView(this);
            espacio.setText(" ");
            letrasLayout.addView(espacio);
        }
    }

    private boolean comprobarFraseCorrecta(Button botonPulsado, Button casillaElegida, DFButton casillaElegidaSolucion) {
        char letraPulsada = botonPulsado.getText().charAt(0);
        char letraSolucion = casillaElegidaSolucion.getLetra();
        if (letraPulsada != letraSolucion) {
            //ERROR
            return false;
        }
        else {
            casillaElegida.setText(letraSolucion+"");
            return true;
        }
    }

    private void ponerFrasePorPantalla() {
        int index = 0;
        for (int i = 0; i < fraseProblema.length; i++) {
            System.out.println(panelDescubrirFrase.getChildAt(i));
            Button boton = (Button) panelDescubrirFrase.getChildAt(i);
            DFButton botonSolucion = new DFButton(boton, frase.getFrase().toCharArray()[index]);
            boton.setText(fraseProblema[i] + "");
            if ((fraseProblema[i] + "").matches("[A-Z]*") ||
                    (fraseProblema[i] + "").matches("[a-z]*"))
            {
                boton.setBackgroundColor(Color.WHITE);
            } else if (fraseProblema[i] == '_') {
                boton.setBackgroundColor(Color.WHITE);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        casillaElegida = boton;
                        casillaElegidaSolucion = botonSolucion;
                        casillaElegida.setBackgroundColor(Color.RED);//COLOR PULSADO
                    }
                });
            }
            else {
                boton.setBackground(getDrawable(R.drawable.botones_teclado));
                boton.setClickable(false);
            }
            index++;
        }

    }

    public void GuideAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("PULSE FUERA DEL RECUADRO PARA EMPEZAR")
                .setCancelable(true);

        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                for (int i = 0; i < panelDescubrirFrase.getChildCount(); i++) {
                    ((Button) panelDescubrirFrase.getChildAt(i)).setText("");
                    ((Button) panelDescubrirFrase.getChildAt(i)).setBackground(getDrawable(R.drawable.botones_teclado));

                    ponerFrasePorPantalla();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
