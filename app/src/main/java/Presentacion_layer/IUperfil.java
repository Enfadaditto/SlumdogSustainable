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

import Builder.CreadorDeJuego;
import Builder.Juego;
import Builder.JuegoBuilder;
import Builder.JuegoRetoPregunta;
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
        ponerDatosUsuario();;


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



    public void PasswordError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Contraseñas diferentes o sin rellenar")
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void cambiarContraseña(View view){
        if (contraseña.getText() != contraseñaRepe.getText() || contraseña.getText() == null) {
            PasswordError();
            return;
            /*errorLabel.setVisible(true)*/}
        if(MainActivity.user.passwordIsSafe(contraseña.getText().toString())){
            UserRepository u = new UserRepository(MainActivity.conexion);
            MainActivity.user.setPassword(contraseña.getText().toString());
            u.actualizar(MainActivity.user);

        }

    }

    public void cerrarSesion(View view){
        MainActivity.user = null;
        Intent intent = new Intent(IUperfil.this, IUuserLogin.class);
        startActivity(intent);


    }

}
