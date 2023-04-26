package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import org.w3c.dom.Text;

import Domain_Layer.Ahorcado;


public class IUretoAhorcado extends AppCompatActivity {

    Ahorcado ahorcado;
    String fraseAhorcado;
    String fraseEnBarrasBajas = "";
    TextView texto_fraseADescubir;
    Button botonSeleccionado;
    Button botonSeleccionado1;
    Button botonSeleccionado2;
    Button botonSeleccionado3;
    Button botonSeleccionado4;
    Button botonSeleccionado5;
    Button botonSeleccionado6;
    Button botonSeleccionado7;
    Button botonSeleccionado8;
    Button botonSeleccionado9;
    Button botonSeleccionado10;
    Button botonSeleccionado11;
    Button botonSeleccionado12;
    Button botonSeleccionado13;
    Button botonSeleccionado19;
    Button botonSeleccionado14;
    Button botonSeleccionado15;
    Button botonSeleccionado16;
    Button botonSeleccionado17;
    Button botonSeleccionado18;
    Button botonSeleccionado20;
    Button botonSeleccionado21;
    Button botonSeleccionado22;
    Button botonSeleccionado23;
    Button botonSeleccionado24;
    Button botonSeleccionado25;
    Button botonSeleccionado26;
    ImageView imagenAhorcado;
    ProgressBar timeBar2;
    CountDownTimer mCountDownTimer;

    TextView enunciado;




    int imax =0;
    int timeCount = 0;
    int Tiempo = 120000;
    int errores = 0;
    char[] letrasEncontradas = new char[20];
    char[] fraseACompletar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_ahorcado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        enunciado = (TextView)findViewById(R.id.enunciado);
        imagenAhorcado = findViewById(R.id.imagenAhorcado);
        botonSeleccionado1 = (Button)findViewById(R.id.botonA);
        botonSeleccionado2= (Button)findViewById(R.id.botonB);
        botonSeleccionado3 = (Button)findViewById(R.id.botonC);
        botonSeleccionado4= (Button)findViewById(R.id.botonD);
        botonSeleccionado5 = (Button)findViewById(R.id.botonE);
        botonSeleccionado6= (Button)findViewById(R.id.botonF);
        botonSeleccionado7 = (Button)findViewById(R.id.botonG);
        botonSeleccionado8 = (Button)findViewById(R.id.botonH);
        botonSeleccionado9 = (Button)findViewById(R.id.botonI);
        botonSeleccionado10 = (Button)findViewById(R.id.botonJ);
        botonSeleccionado11 = (Button)findViewById(R.id.botonK);
        botonSeleccionado12 = (Button)findViewById(R.id.botonL);
        botonSeleccionado13 = (Button)findViewById(R.id.botonM);
        botonSeleccionado14 = (Button)findViewById(R.id.botonN);
        botonSeleccionado15 = (Button)findViewById(R.id.botonO);
        botonSeleccionado16 = (Button)findViewById(R.id.botonP);
        botonSeleccionado17 = (Button)findViewById(R.id.botonQ);
        botonSeleccionado18 = (Button)findViewById(R.id.botonR);
        botonSeleccionado19 = (Button)findViewById(R.id.botonS);
        botonSeleccionado20 = (Button)findViewById(R.id.botonT);
        botonSeleccionado21 = (Button)findViewById(R.id.botonU);
        botonSeleccionado22 = (Button)findViewById(R.id.botonV);
        botonSeleccionado23 = (Button)findViewById(R.id.botonW);
        botonSeleccionado24 = (Button)findViewById(R.id.botonX);
        botonSeleccionado25 = (Button)findViewById(R.id.botonY);
        botonSeleccionado26 = (Button)findViewById(R.id.botonZ);



        botonSeleccionado = (Button)findViewById(R.id.botonA);
        texto_fraseADescubir = findViewById(R.id.texto_fraseADescubir);
        //fraseAhorcado = ahorcado.getPalabra();
        cargarDatos();
        //fraseAhorcado = "PAPAS CON QUESO";

        System.out.println(fraseAhorcado);
        fraseACompletar = new char [fraseAhorcado.length()];
        for(int i = 0; i<fraseAhorcado.length();i++){
            if(fraseAhorcado.charAt(i) == ' '){
                fraseACompletar[i]= ' ';
            }else{
                fraseACompletar[i]= '_';
            }

           fraseEnBarrasBajas += fraseACompletar[i]+" ";
        }
        texto_fraseADescubir.setText(fraseEnBarrasBajas.trim());

        }

        //IniciarBaseDedatos();

    public void cargarDatos() {
        Bundle extras = getIntent().getExtras();

        fraseAhorcado = extras.getString("palabraAhorcado").toUpperCase();;
        String enunciadoString = extras.getString("enunciadoAhorcado");
        enunciado.setText(enunciadoString);

        Tiempo = extras.getInt("Tiempo");

    }

    public void botonSelecionado(View v){
        String letraSeleccionada= "";
        Button b = (Button) v;
        letraSeleccionada = String.valueOf(((Button) v).getText());
        validarLetra(letraSeleccionada, b);
    }

    public void validarLetra(String letraSeleccionada, Button botonSelccionado){
        if (fraseAhorcado.contains(letraSeleccionada)){
            //letra correcta
            botonSelccionado.setBackground(getDrawable(R.drawable.boton_teclado_verde));
            letrasEncontradas[imax++]= StringToChar(letraSeleccionada);
            sustituirLetra(letraSeleccionada);
        }else {
            //letra incorrecta
            botonSelccionado.setBackground(getDrawable(R.drawable.boton_teclado_rojo));
            errores++;
            ponerImagenAhorcado();
        }
        botonSelccionado.setEnabled(false);
    }
    public void ponerImagenAhorcado(){
        int Ahorcado = getResources().getIdentifier("ahorcado_" + errores, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(Ahorcado);
        imagenAhorcado.setImageDrawable(imagen);
    }

    public void sustituirLetra(String letraSeleccionada){

        String textoEnPantalla= "";
        for(int i = 0; i<letrasEncontradas.length;i++){
            for(int j =0 ;j<fraseAhorcado.length(); j++){

                if(letrasEncontradas[i]==fraseAhorcado.charAt(j)){
                    fraseACompletar[j] = fraseAhorcado.charAt(j);
                }

            }
        }
            fraseEnBarrasBajas="";
       for(int i =0 ;i<fraseAhorcado.length(); i++){
            fraseEnBarrasBajas += fraseACompletar[i]+" ";
        }

        texto_fraseADescubir.setText(fraseEnBarrasBajas.trim());
    }

    public static char StringToChar(String s) {
        return s.charAt(0);
    }
/*
    private void IniciarBaseDedatos() {
        new Thread(new Runnable() {
            public void run() {
                try {

                    ahorcado = new Ahorcado();
                    preguntasEnBD = new QuestionRepository();
                    preguntaActual = preguntasEnBD.obtener(QuestionID);
                    respuestasActuales = preguntasEnBD.getRespuestasPregunta(preguntaActual);


                    runOnUiThread(new Runnable() {
                        public void run() {
                            //ponerTextoEnPantalla();
                        }
                    });
                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        }).start();


    }

 */

}
