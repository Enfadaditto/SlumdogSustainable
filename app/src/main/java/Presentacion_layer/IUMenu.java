package Presentacion_layer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

public class IUMenu extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_reto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void clickBotonRetoAhorcado(View v){
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoAhorcado");
        I.putExtras(b);
        startActivity(I);
    }

    public void clickBotonRetoPregunta(View v){
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoPregunta");
        I.putExtras(b);
        startActivity(I);
    }

    public void clickBotonRetoDescubrirFrase(View v){
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoFrase");
        I.putExtras(b);
        startActivity(I);
    }

    public void clickBotonRetoMixto(View v) {
        Intent I = new Intent(this, FachadaDeRetos.class);
        Bundle b = new Bundle();
        b.putString("tipoReto", "RetoMixto");
        I.putExtras(b);
        startActivity(I);
    }

    int contadorClick = 0;
    public void easterEggOnClick(View v) {
        contadorClick++;
        if (contadorClick == 5) ;//notificarYEliminarObservador(MainActivity.user, 29);
    }
}
