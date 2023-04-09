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





    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        nombreUsuario = findViewById(R.id.nombreUsuario);
        contraseña = findViewById(R.id.contraseña);
        contraseñaRepe = findViewById(R.id.contraseñaRepe);
        cambiarContraseña = findViewById(R.id.cambiarContraseña);
        ponerDatosUsuario();;


    }
    public void ponerDatosUsuario(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(MainActivity.conexion);
                nombreUsuario.setText(MainActivity.user.getNickname());
                contraseña.setText(MainActivity.user.getPassword());
                contraseñaRepe.setText(MainActivity.user.getPassword());
                u.actualizar(MainActivity.user);
            }
        }).start();

    }


    public void PasswordError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Contraseñas diferentes")
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
    public void cambiarContraseña(){
        if (contraseña.getText() != contraseñaRepe.getText()) {
            PasswordError();
            return;
            /*errorLabel.setVisible(true)*/}

    }

}
