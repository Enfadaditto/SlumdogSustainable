package Domain_Layer.Patron_Fabrica;

import android.content.Context;

import Domain_Layer.Logro;


public class FabricaConcreta extends IFabricaBocadillos{
    @Override
    public BocadilloLogro crearProducto(Context context, Logro logro) {
        BocadilloLogro bocadillo = new BocadilloLogro(context, logro);

        bocadillo.crearImagen();
        bocadillo.crearSeparador();
        bocadillo.crearLayoutTextos();
        bocadillo.crearTextos();
        bocadillo.addAlBocadillo();

        return bocadillo;
    }
}
