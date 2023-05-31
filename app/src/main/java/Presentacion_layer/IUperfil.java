package Presentacion_layer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import Domain_Layer.Answer;
import Domain_Layer.CustomSpinnerAdapter;
import Domain_Layer.Question;
import Domain_Layer.User;
import Persistence.AnswerRepository;
import Persistence.QuestionRepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
public class IUperfil extends AppCompatActivity {
    private EditText nombreUsuario;
    private EditText contraseña;
    private EditText contraseñaRepe;
    private Button cambiarContraseña;
    private ImageView imagenPerfil;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        nombreUsuario = findViewById(R.id.nombreUsuario);
        nombreUsuario.setEnabled(false);
        contraseña = findViewById(R.id.contraseña);
        contraseñaRepe = findViewById(R.id.contraseñaRepe);
        cambiarContraseña = findViewById(R.id.cambiarContraseña);
        Spinner spinner = findViewById(R.id.spinnerIcon);

        List<Integer> imageIds = Arrays.asList(R.drawable.icon1, R.drawable.icon2, R.drawable.icon3);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, imageIds);
        spinner.setAdapter(adapter);
        spinner.setSelection(MainActivity.user.getIcon());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    updateIcon(0);
                }

                if(i == 1) {
                    updateIcon(1);
                }

                if(i == 2) {
                    updateIcon(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void updateIcon(int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                MainActivity.user.setIcon(i);
                u.actualizar(MainActivity.user);
            }
        }).start();
    }
    public void ponerDatosUsuario() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                nombreUsuario.setText(MainActivity.user.getNickname());
                //if (MainActivity.user.getIcon() != null) imagenPerfil.setImageBitmap(MainActivity.user.getIcon());
            }
        }).start();

    }


    public void PasswordError(String errorString) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(errorString)
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void cambiarContraseña(View view) {
        if (!contraseña.getText().toString().trim().equals(contraseñaRepe.getText().toString().trim())) {
            runOnUiThread(new Runnable() {
                public void run() {
                    PasswordError("Contraseñas diferentes");
                }
            });
            return;
        }
        if (!contraseña.getText().toString().matches("^(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$")) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ErrorAlert("Tu contraseña no es lo suficientemente segura, debe tener una mayúscula, una minúscula, mínimo 8 carácteres, y uno de ellos especial.");
                }
            });

            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cambiarContraseña(MainActivity.user, contraseña.getText().toString());
                } catch (Exception e) {
                }
            }
        }).start();

        Intent intent = new Intent(IUperfil.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cambiarContraseña(User usuarioActual, String contraseña) {
        usuarioActual.setPassword(contraseña);
        UserRepository s = new UserRepository((SingletonConnection.getSingletonInstance()));
        s.actualizar(usuarioActual);
    }

    public void clickInfoContraseña(View v) {
        ErrorAlert("La contraseña debe tener una mayúscula, una minúscula, mínimo 8 carácteres, y uno de ellos especial.");
    }

    public void ErrorAlert(String errorString) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(errorString)
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void cerrarSesion(View view) {
        MainActivity.user = null;
        Intent intent = new Intent(IUperfil.this, IUuserLogin.class);
        startActivity(intent);
        finish();
    }

    public void abandonarPerfil(View v){
        finish();
    }
}
