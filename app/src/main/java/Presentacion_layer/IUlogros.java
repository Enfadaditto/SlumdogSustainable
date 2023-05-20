package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User_has_Logro;
import Persistence.AnswerRepository;
import Persistence.LogroRepository;
import Persistence.QuestionRepository;
import Persistence.SingletonConnection;
import Persistence.User_LRepository;

public class IUlogros extends AppCompatActivity {
    List<Logro> todosLosLogros; List<User_has_Logro> listaEnlaces;
    User_has_Logro enlaceLogroUsuario = new User_has_Logro(null, -1);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logros);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            IniciarBaseDedatos();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ponerNombresLogros();
        ponerDescripciones();


    }

    public void ponerNombresLogros(){
        for (int i = 1; i <= 33; i++) {
            int textViewId = getResources().getIdentifier("textoLogro" + i, "id", getPackageName());
            TextView textView = findViewById(textViewId);
            textView.setText(todosLosLogros.get(i - 1).getNombre());
        }
    }

    public void ponerDescripciones() {
        for (int i = 1; i <= 33; i++) {
            int textViewId = getResources().getIdentifier("descripcion" + i, "id", getPackageName());
            TextView textView = findViewById(textViewId);
            textView.setText(todosLosLogros.get(i - 1).getDescripcion());


        }
    }

    public void ponerIconos() {
        for (int i = 1; i <= 33; i++) {
            int imageViewId = getResources().getIdentifier("imagenLogro" + i, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);
            User_has_Logro enlace = enlaceLogroUsuario.getEnlaceUsuarioLogro(MainActivity.user.getNickname(), i, listaEnlaces);
            if (enlace.isCompletado()) {
                if (todosLosLogros.get(i).getTipo() == Logro.TipoLogro.Medalla) imageView.setImageDrawable(null /*MEDALLA*/);
                if (todosLosLogros.get(i).getTipo() == Logro.TipoLogro.Trofeo) imageView.setImageDrawable(null /*TROFEO*/);
            }


        }
    }
    private void IniciarBaseDedatos() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    todosLosLogros = new LogroRepository(SingletonConnection.getSingletonInstance()).getAllLogros();
                    listaEnlaces = new User_LRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        t.start();
        t.join();
    }
}
