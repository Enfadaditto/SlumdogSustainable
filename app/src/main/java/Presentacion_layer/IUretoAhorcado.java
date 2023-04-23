package Presentacion_layer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import Domain_Layer.Ahorcado;


public class IUretoAhorcado extends AppCompatActivity {

    Ahorcado ahorcado;
    String fraseAhorcado;
    String fraseProvisional = "";
    TextView texto_fraseADescubir;
    Button botonSeleccionado;
    LinearLayout l;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_ahorcado);
/*

        botonSeleccionado = (Button)findViewById(R.id.A);
        botonSeleccionado = (Button)findViewById(R.id.B);
        botonSeleccionado = (Button)findViewById(R.id.C);
        botonSeleccionado = (Button)findViewById(R.id.D);
        botonSeleccionado = (Button)findViewById(R.id.E);
        botonSeleccionado = (Button)findViewById(R.id.F);
        botonSeleccionado = (Button)findViewById(R.id.G);
        botonSeleccionado = (Button)findViewById(R.id.H);
        botonSeleccionado = (Button)findViewById(R.id.I);
        botonSeleccionado = (Button)findViewById(R.id.J);
        botonSeleccionado = (Button)findViewById(R.id.K);
        botonSeleccionado = (Button)findViewById(R.id.L);
        botonSeleccionado = (Button)findViewById(R.id.M);
        botonSeleccionado = (Button)findViewById(R.id.N);
        botonSeleccionado = (Button)findViewById(R.id.O);
        botonSeleccionado = (Button)findViewById(R.id.P);
        botonSeleccionado = (Button)findViewById(R.id.Q);
        botonSeleccionado = (Button)findViewById(R.id.R);
        botonSeleccionado = (Button)findViewById(R.id.S);
        botonSeleccionado = (Button)findViewById(R.id.T);
        botonSeleccionado = (Button)findViewById(R.id.U);
        botonSeleccionado = (Button)findViewById(R.id.V);
        botonSeleccionado = (Button)findViewById(R.id.W);
        botonSeleccionado = (Button)findViewById(R.id.X);
        botonSeleccionado = (Button)findViewById(R.id.Y);
        botonSeleccionado = (Button)findViewById(R.id.Z);


        l = (LinearLayout) findViewById(R.id.keyboard);



 */



        texto_fraseADescubir = findViewById(R.id.texto_fraseADescubir);
        //fraseAhorcado = ahorcado.getPalabra();
        fraseAhorcado = "papa";

        for(int i = 0; i<fraseAhorcado.length();i++){

            fraseProvisional+= "_ ";

        }
        texto_fraseADescubir.setText(fraseProvisional.trim());
        //IniciarBaseDedatos();
    }

    public void botonSelecionado(View v){



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
