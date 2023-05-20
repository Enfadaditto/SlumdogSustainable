package Patron_Fabrica;

import android.content.Context;

import Domain_Layer.Logro;

public abstract class IFabricaBocadillos {
    public abstract IBocadilloLogro crearProducto(Context context, Logro logro);
}
