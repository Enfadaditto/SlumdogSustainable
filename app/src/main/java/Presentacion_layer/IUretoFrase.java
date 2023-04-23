package Presentacion_layer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.slumdogsustainable.R;

public class IUretoFrase extends AppCompatActivity {

    Button b;

    LinearLayout l;

    float x,y;

    float dx,dy;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_frase);
        b = (Button)findViewById(R.id.J);
        l = (LinearLayout) findViewById(R.id.principal);



    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            b.bringToFront();

        }

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            dx = event.getX() -x;
            dy = event.getY() -y;

            b.setX(b.getX() + dx);
            b.setY(b.getY() + dy);

            b.bringToFront();
            x = event.getX();
            y = event.getY();
        }

        return super.onTouchEvent(event);
    }


}
