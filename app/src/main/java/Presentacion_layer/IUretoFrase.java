package Presentacion_layer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SyncAdapterType;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

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
    TextView descripcionFrase;
    boolean ultimaAcertada;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        frase = new Frase("Probando frase, (ODS 14)", 0, "Facil");

        letrasLayout = (LinearLayout) findViewById(R.id.letrasLayout);
        panelDescubrirFrase = (RelativeLayout) findViewById(R.id.panelDescubrirFrase);
        descripcionFrase = (TextView) findViewById(R.id.descripcionFrase);

        fraseProblema = frase.getFraseProblema();
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);
        casillaElegida = new DFButton(this);

        rellenarLetrasLayout(listadoCaracteresFrase);
        letrasLayout.setVisibility(View.INVISIBLE);

        descripcionFrase.setText("Haz click aqui para comenzar");
        descripcionFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComenzarReto();
                letrasLayout.setVisibility(View.VISIBLE);
                descripcionFrase.setText("DESCRIPCION");
            }
        });
    }

    private void ComenzarReto() {
        for (int i = 0; i < panelDescubrirFrase.getChildCount(); i++) {
            ((Button) panelDescubrirFrase.getChildAt(i)).setText("");
            ((Button) panelDescubrirFrase.getChildAt(i)).setBackground(getDrawable(R.drawable.botones_teclado));
        }
        ponerFrasePorPantalla();
        //COMENZAR TIEMPO
    }

    private void rellenarLetrasLayout(List<Character> listado)  {
        for (char letra : listado) {
            Button boton = new Button(this);
            boton.setLayoutParams(letrasLayout.getLayoutParams());
            boton.setBackground(getDrawable(R.drawable.botones_teclado));
            boton.setText(letra+""); boton.setTextColor(Color.WHITE);
            boton.setTextSize(48);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) boton.getLayoutParams();
            float dp = getResources().getDisplayMetrics().density;
            int newMargin = (int) (2 * dp);
            layoutParams.setMargins(newMargin, 0, newMargin, 0);
            boton.setLayoutParams(layoutParams);

            boton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(comprobarFraseCorrecta((Button) v, casillaElegida, casillaElegidaSolucion)) {
                        letrasLayout.removeView(v);
                        ultimaAcertada = true;
                    }
                }
            });
            letrasLayout.addView(boton);
        }
    }

    private boolean comprobarFraseCorrecta(Button botonPulsado, Button casillaElegida, DFButton casillaElegidaSolucion) {
        char letraPulsada = botonPulsado.getText().charAt(0);
        char letraSolucion = casillaElegidaSolucion.getLetra();
        if (!(letraPulsada+"").toUpperCase().equals((letraSolucion+"").toUpperCase())) {
            //ERROR
            return false;
        }
        else {
            casillaElegida.setBackgroundColor(Color.LTGRAY);
            casillaElegida.setClickable(false);
            casillaElegida.setText(letraSolucion+"");
            casillaElegidaSolucion.setLetra('*');
            return true;
        }
    }

    private void ponerFrasePorPantalla() {
        int index = 0;
        String[] palabras = String.valueOf(fraseProblema).split(" ");

        for (int i = 0; i < fraseProblema.length; i++) {
            Button boton = (Button) panelDescubrirFrase.getChildAt(i);
            DFButton botonSolucion = new DFButton(boton, frase.getFrase().toCharArray()[index]);
            boton.setText(fraseProblema[i] + "");
            if ((fraseProblema[i] + "").matches("[A-Z]*") ||
                    (fraseProblema[i] + "").matches("[a-z]*"))
                boton.setBackgroundColor(Color.LTGRAY);
            else if (fraseProblema[i] == '_') {
                boton.setBackgroundColor(Color.WHITE);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ultimaAcertada) casillaElegida.setBackgroundColor(Color.WHITE);
                        ultimaAcertada = false;
                        casillaElegida = boton;
                        casillaElegidaSolucion = botonSolucion;
                        casillaElegida.setBackgroundColor(Color.LTGRAY);//COLOR PULSADO
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
