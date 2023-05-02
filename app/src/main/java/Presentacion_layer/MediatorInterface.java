package Presentacion_layer;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface MediatorInterface {

    public void iniciarDatosRetoDescubrirFrase();


    public void iniciarDatosRetoPregunta();

    public void iniciarDatosRetoAhorcado();

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
