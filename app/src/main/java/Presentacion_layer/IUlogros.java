package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.R;

import java.util.ArrayList;
import java.util.List;

public class IUlogros extends AppCompatActivity {

    List<TextView> enunciadosLogros = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logros);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        for (int i = 1; i <= 10; i++) {
            int textViewId = getResources().getIdentifier("textoLogro" + i, "id", getPackageName());
            TextView textView = findViewById(textViewId);
            ponerNombresLogros(textView);

        }


    }

    public void ponerNombresLogros(TextView logro){
        logro.setText("vaya vaya");
    }

}
