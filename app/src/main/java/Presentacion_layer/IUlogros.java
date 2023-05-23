package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User_has_Logro;
import Persistence.LogroRepository;
import Persistence.SingletonConnection;
import Persistence.User_LRepository;

public class IUlogros extends AppCompatActivity {
    List<Logro> todosLosLogros; List<User_has_Logro> listaEnlaces;
    List<Logro> todosLosTrofeos = new ArrayList<>();
    User_has_Logro enlaceLogroUsuario = new User_has_Logro(null, -1);
    List<Logro> todasLasMedallas  = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logros);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            IniciarBaseDedatos();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        iniciarListas();
        ponerLogros(todosLosLogros);


    }


    public void ponerLogros(List<Logro> logrosAPoner){
        //List<Logro> todasLasMedallas = listaMedallas();
        int numLogros =  logrosAPoner.size();
        for (int i = 1; i <= numLogros; i++) {

            int textNombre = getResources().getIdentifier("textoLogro" + i, "id", getPackageName());
            TextView textView1 = findViewById(textNombre);
            textView1.setText(logrosAPoner.get(i - 1).getNombre());
            ponerDescripciones(logrosAPoner, i);
            ponerIconos(logrosAPoner, i);



        }
        ocultarContenedoresExtra(numLogros);

    }

    public void ocultarContenedoresExtra(int tamaño){
        for(int i = 1; i <= 40 ; i++){
            int relativeLayout = getResources().getIdentifier("relativeLayout" + i, "id", getPackageName());
            RelativeLayout contenedor = findViewById(relativeLayout);
            if(i > tamaño) {
                contenedor.setVisibility(View.GONE);
            }else{
                contenedor.setVisibility(View.VISIBLE);
            }
        }



    }

    public void ponerDescripciones(List<Logro> lista, int i) {
        int textDescripcion = getResources().getIdentifier("descripcion" + i, "id", getPackageName());
        TextView textView2 = findViewById(textDescripcion);
        textView2.setText(lista.get(i - 1).getDescripcion());

    }

    public void ponerIconos(List<Logro> lista, int i) {
            int imageViewId = getResources().getIdentifier("imagenLogro" + i, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);
            User_has_Logro enlace = enlaceLogroUsuario.getEnlaceUsuarioLogro(MainActivity.user.getNickname(), i, listaEnlaces);
            if (enlace.isCompletado()) {
                if (lista.get(i - 1).getTipo() == Logro.TipoLogro.Medalla) imageView.setImageDrawable(getDrawable(R.drawable.logroconseguido));
                if (lista.get(i - 1).getTipo() == Logro.TipoLogro.Trofeo) imageView.setImageDrawable(getDrawable(R.drawable.trofeo_ganado));
            }
            else{
                if (lista.get(i - 1).getTipo() == Logro.TipoLogro.Medalla) imageView.setImageDrawable(getDrawable(R.drawable.logronoconseguido));
                if (lista.get(i - 1).getTipo() == Logro.TipoLogro.Trofeo) imageView.setImageDrawable(getDrawable(R.drawable.trofeo_noconseguido));
            }
    }


    private void IniciarBaseDedatos() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    todosLosLogros = Logro.getAllLogros(new LogroRepository(SingletonConnection.getSingletonInstance()));
                    listaEnlaces = new User_LRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        t.start();
        t.join();
    }

    public void iniciarListas(){
        for(Logro l : todosLosLogros){
            if(l.getTipo() == Logro.TipoLogro.Medalla){
                todasLasMedallas.add(l);
            }else{
                todosLosTrofeos.add(l);
            }
        }


    }


    public void onClickMedalla(View view){ponerLogros(todasLasMedallas);}
    public void onClickTrofeo(View view){
        ponerLogros(todosLosTrofeos);
    }
    public void onClickTodos(View view){
        ponerLogros(todosLosLogros);
    }
}
