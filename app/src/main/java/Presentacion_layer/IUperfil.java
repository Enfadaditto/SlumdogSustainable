package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import Domain_Layer.Answer;
import Domain_Layer.Question;
import Domain_Layer.User;
import Persistence.AnswerRepository;
import Persistence.QuestionRepository;
import Persistence.UserRepository;
public class IUperfil extends AppCompatActivity{
    private EditText nombreUsuario;
    private EditText contraseña;
    private EditText contraseñaRepe;
    private Button cambiarContraseña;
    private ImageView imagenPerfil;





    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        nombreUsuario = findViewById(R.id.nombreUsuario);
        nombreUsuario.setEnabled(false);
        contraseña = findViewById(R.id.contraseña);
        contraseñaRepe = findViewById(R.id.contraseñaRepe);
        cambiarContraseña = findViewById(R.id.cambiarContraseña);
        imagenPerfil = findViewById(R.id.imagenPerfil);
        ponerDatosUsuario();


    }
    public void ponerDatosUsuario(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                nombreUsuario.setText(MainActivity.user.getNickname());
                contraseña.setText("");
                contraseñaRepe.setText("");
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
                    PasswordError("Tu contraseña da asco, duchate");
                }
            });
            return;
        }
            if (MainActivity.user.passwordIsSafe(contraseña.getText().toString())) {
                /*UserRepository u = new UserRepository(MainActivity.conexion);
                User userActual = MainActivity.user;
                userActual.setPassword(contraseña.getText().toString());

                u.actualizar(userActual);*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cambiarContraseña(MainActivity.user, contraseña.getText().toString());
                        }catch (Exception e){

                        }
                    }
                }).start();


                Intent intent = new Intent(IUperfil.this, MainActivity.class);
                startActivity(intent);



        }
    }
    public void cambiarContraseña(User usuarioActual, String contraseña){
        usuarioActual.setPassword(contraseña);
        UserRepository s = new UserRepository((MainActivity.conexion));
        s.actualizar(usuarioActual);


    }

    public void cerrarSesion(View view){
        MainActivity.user = null;
        Intent intent = new Intent(IUperfil.this, IUuserLogin.class);
        startActivity(intent);


    }

}
