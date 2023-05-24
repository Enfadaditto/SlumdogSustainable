package Presentacion_layer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import Patron_Fachada.FachadaDeRetos;
import Persistence.ObjetivoSemanal;

public class IUMenu extends AppCompatActivity{


    Button botonRetoAhorcado;
    Button botonRetoFrase;
    Button botonRetoMixto;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_reto);

        botonRetoAhorcado = findViewById(R.id.botonRetoAhorcado);
        botonRetoFrase = findViewById(R.id.botonRetoFrase);
        botonRetoMixto = findViewById(R.id.botonRetoMixto);
        //habilitarBotones();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ObjetivoSemanal.sumarMetaSemanal();
    }

    public void clickBotonRetoAhorcado(View v){
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoAhorcado");
        I.putExtras(b);
        startActivityForResult(I, 100);
    }

    public void clickBotonRetoPregunta(View v){
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoPregunta");
        I.putExtras(b);
        startActivityForResult(I, 100);
    }

    public void clickBotonRetoDescubrirFrase(View v){
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoFrase");
        I.putExtras(b);
        startActivityForResult(I, 100);
    }

    public void clickBotonRetoMixto(View v) {
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoMixto");
        I.putExtras(b);
        startActivityForResult(I, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            finish();
        }
    }

    int contadorClick = 0;
    public void easterEggOnClick(View v) {
        contadorClick++;
        if (contadorClick == 5) ;//notificarYEliminarObservador(MainActivity.user, 29);
    }

    public int desbloqueoDeNiveles(){
        if(MainActivity.user.getPointsAchieved()>5000 && MainActivity.user.getPointsAchieved()<10000)  {
            return 1;
        }else if(MainActivity.user.getPointsAchieved()>=10000){
            return 2;
        }

        return 0;
    }



    public void habilitarBotones(){

        if(desbloqueoDeNiveles() == 0){
            botonRetoAhorcado.setText("Necesitas nivel 1");
            botonRetoAhorcado.setEnabled(false);
            botonRetoFrase.setText("Necesitas nivel 2");
            botonRetoFrase.setEnabled(false);
            botonRetoMixto.setText("Necesitas nivel 3");
            botonRetoMixto.setEnabled(false);
        } else if (desbloqueoDeNiveles() == 1) {
            botonRetoFrase.setText("Necesitas nivel 2");
            botonRetoFrase.setEnabled(false);
            botonRetoMixto.setText("Necesitas nivel 3");
            botonRetoMixto.setEnabled(false);
        } else if(desbloqueoDeNiveles() == 2){
            botonRetoMixto.setText("Necesitas nivel 3");
            botonRetoMixto.setEnabled(false);
        }

    }
}
