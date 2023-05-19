package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Logro;
import Persistence.AnswerRepository;
import Persistence.LogroRepository;
import Persistence.QuestionRepository;
import Persistence.SingletonConnection;

public class IUlogros extends AppCompatActivity {
    List<Logro> todosLosLogros;


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
    private void IniciarBaseDedatos() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    todosLosLogros = new LogroRepository(SingletonConnection.getSingletonInstance()).getAllLogros();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        t.start();
        t.join();
    }
}
