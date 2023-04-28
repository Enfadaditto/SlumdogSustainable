package Presentacion_layer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SyncAdapterType;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

    String fraseEnunciado;
    String descripcionEnunciado;
    int PuntosTotales, PuntosConsolidados, Vidas, Ronda;
    boolean haConsolidado;
    int TiempoOpcion, Tiempo, Nivel, SonidoFallo, SonidoAcierto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        frase = new Frase("", 0, "");
        cargarDatos();

        letrasLayout = (LinearLayout) findViewById(R.id.letrasLayout);
        panelDescubrirFrase = (RelativeLayout) findViewById(R.id.panelDescubrirFrase);
        descripcionFrase = (TextView) findViewById(R.id.descripcionFrase);

        fraseProblema = frase.getFraseProblema();
        listadoCaracteresFrase = frase.letrasDeLaFrase();
        Collections.shuffle(listadoCaracteresFrase);
        casillaElegida = new DFButton(this);

        rellenarLetrasLayout(listadoCaracteresFrase);
        letrasLayout.setVisibility(View.INVISIBLE);

        descripcionFrase.setText("Haz click AQUI para comenzar");
        descripcionFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComenzarReto();
                letrasLayout.setVisibility(View.VISIBLE);
                descripcionFrase.setText(descripcionEnunciado);
                descripcionFrase.setOnClickListener(null);
            }
        });
        casillaElegidaSolucion = new DFButton(new Button(this), '*');
    }

    public void cargarDatos() {
        Bundle extras = getIntent().getExtras();

        fraseEnunciado = extras.getString("fraseEnunciado");
        descripcionEnunciado = extras.getString("descripcionEnunciado");
        PuntosTotales = extras.getInt("PuntosTotales");
        PuntosConsolidados = extras.getInt("PuntosConsolidados");
        Vidas = extras.getInt("Vidas");
        Ronda = extras.getInt("Ronda");
        haConsolidado = extras.getBoolean("haConsolidado");
        Tiempo = extras.getInt("Tiempo");
        TiempoOpcion = extras.getInt("TiempoOpcion");
        Nivel = extras.getInt("Nivel");
        SonidoAcierto = extras.getInt("SonidoAcierto");
        SonidoFallo = extras.getInt("SonidoFallo");
        Tiempo = extras.getInt("Tiempo");

        frase.setFrase(fraseEnunciado);
        //poner_imagen_ods();
    }


    private void ComenzarReto() {
        for (int i = 0; i < panelDescubrirFrase.getChildCount(); i++) {
            ((Button) panelDescubrirFrase.getChildAt(i)).setText("");
            ((Button) panelDescubrirFrase.getChildAt(i)).setBackground(getDrawable(R.drawable.boton_panel_vacio));
        }
        ponerFrasePorPantalla();
        //startTimer()
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
        int indice = 0;
        String[] palabras = String.valueOf(fraseProblema).split(" ");
        String[] palabrasSolucion = String.valueOf(frase.getFrase()).split(" ");;

        for (int i = 0; i < palabras.length; i++) {
            if (i == -1) return;
            System.out.println(palabras[i] + " :: " + palabrasSolucion[i]);
            indice = escribirPalabra(palabras[i].toCharArray(), palabrasSolucion[i].toCharArray(), indice);
            ++indice;
        }

    }

    private int escribirPalabra(char[] palabraActual, char[] palabraActualSolucion, int indice) {
        if (((indice % (panelDescubrirFrase.getChildCount()/3)) + palabraActual.length) > (panelDescubrirFrase.getChildCount()/3) ) {
            if (indice < (panelDescubrirFrase.getChildCount()/3))
                return escribirPalabra(palabraActual, palabraActualSolucion,(panelDescubrirFrase.getChildCount()/3));
            else if (indice < 2*(panelDescubrirFrase.getChildCount()/3))
                return escribirPalabra(palabraActual, palabraActualSolucion,2*(panelDescubrirFrase.getChildCount()/3));
            else
                return -1;
        }

        for (int i = 0; i < palabraActual.length; i++) {
            Button boton = (Button) panelDescubrirFrase.getChildAt(indice + i);
            DFButton botonSolucion = new DFButton(boton, palabraActualSolucion[i]);
            boton.setText(palabraActual[i] + "");
            if ((palabraActual[i] + "").matches("[A-Z]*") ||
                    (palabraActual[i] + "").matches("[a-z]*"))
                boton.setBackgroundColor(Color.LTGRAY);
            else if (palabraActual[i] == '_') {
                boton.setBackground(getDrawable(R.drawable.boton_panel));
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ultimaAcertada) casillaElegida.setBackgroundColor(Color.WHITE);
                        ultimaAcertada = false;
                        casillaElegida = boton;
                        casillaElegidaSolucion = botonSolucion;
                        casillaElegida.setBackgroundColor(Color.LTGRAY);
                    }
                });
            }
            else {
                boton.setBackground(getDrawable(R.drawable.botones_teclado));
                boton.setClickable(false);
            }
        }
        indice += palabraActual.length;
        return indice;
    }
}
