package Presentacion_layer;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface FalladaInterface {

    void crearRetoDescubrirFrase();


    void crearRetoPregunta();

    void crearRetoAhorcado();

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
