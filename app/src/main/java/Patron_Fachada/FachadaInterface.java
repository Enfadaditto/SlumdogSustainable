package Patron_Fachada;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface FachadaInterface {

    void siguienteRetoDescubrirFrase();

    void siguienteRetoPregunta();

    void siguienteRetoAhorcado();

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}



