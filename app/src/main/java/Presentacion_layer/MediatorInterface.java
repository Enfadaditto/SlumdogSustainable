package Presentacion_layer;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface MediatorInterface {

    public void pasarDatosRetoDescubrirFrase();

    public void pasarDatosRetoPregunta();

    public void pasarDatosRetoAhorcado();

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
