package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import Domain_Layer.CustomSpinnerAdapter;
import Domain_Layer.User;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class IUuserRegister extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nicknameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText repeatPasswordField;
    private ImageView iconSelector;
    private Button registerButton;

    private int icon = 1;
    private final User userActual = new User("a", "b","c", 0);



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        nicknameField = findViewById(R.id.nicknameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        Spinner spinner = findViewById(R.id.spinnerIcon);

        List<Integer> imageIds = Arrays.asList(R.drawable.icon1, R.drawable.icon2, R.drawable.icon3);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, imageIds);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                icon = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        registerButton = findViewById(R.id.registerButton);
    }


    public void registerButtonOnClick(View view) {
        new Thread(new Runnable() {
            public void run(){
                try {
                    if (!User.checkUsernameNotTaken(new UserRepository(SingletonConnection.getSingletonInstance()), nicknameField.getText().toString())) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ErrorAlert("Nombre de usuario en uso");
                            }
                        });
                        return;
                    }
                    if (!passwordField.getText().toString().matches("^(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$")) {
                        runOnUiThread(new Runnable() {
                            public void run() { ErrorAlert("Tu contraseña no es lo suficientemente segura, debe tener una mayúscula, una minúscula, 8 carácteres, y uno de ellos especial."); }
                        });
                        return;
                    }
                    if (!passwordField.getText().toString().trim().equals(repeatPasswordField.getText().toString().trim())) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ErrorAlert("Contraseñas diferentes");
                            }
                        });
                        return;
                    }
                    if (!emailField.getText().toString().matches("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ErrorAlert("Introduce un Email válido, debe ser con el siguiente formato tuemail@dominio.com");
                            }
                        });
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setContentView(R.layout.pantalla_carga);
                        }
                    });

                    new UserRepository(SingletonConnection.getSingletonInstance()).guardar(
                            new User(
                                    nicknameField.getText().toString(),
                                    emailField.getText().toString(),
                                    passwordField.getText().toString(),
                                    icon
                            )
                    );
                    userActual.cargarODSUser(nicknameField.getText().toString());
                    MainActivity.user = User.getUserByUsername(new UserRepository(SingletonConnection.getSingletonInstance()), nicknameField.getText().toString());
                    Intent intent = new Intent(IUuserRegister.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (RuntimeException e) {e.printStackTrace();}
            }
        }).start();
    }

    public void clickInfoEmail(View v) {
        ErrorAlert("El email debe seguir el siguiente formato tuemail@dominio.com");
    }

    public void clickInfoContraseña(View v) {
        ErrorAlert("La contraseña debe tener una mayúscula, una minúscula, 8 carácteres, y uno de ellos especial.");
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
}
