package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.io.ByteArrayOutputStream;

import Domain_Layer.ODS_has_User;
import Domain_Layer.User;
import Persistence.ODS_URepository;
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

    private byte [] userImage;
    private final User userActual = new User("a", "b","c", null);



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        nicknameField = findViewById(R.id.nicknameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        iconSelector = findViewById(R.id.iconSelector);
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

                    new UserRepository(SingletonConnection.getSingletonInstance()).guardar(
                            new User(
                                    nicknameField.getText().toString(),
                                    emailField.getText().toString(),
                                    passwordField.getText().toString(),
                                    userImage
                            )
                    );
                    cargarODSUser(nicknameField.getText().toString());
                    MainActivity.user = User.getUserByUsername(new UserRepository(SingletonConnection.getSingletonInstance()), nicknameField.getText().toString());
                    Intent intent = new Intent(IUuserRegister.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (RuntimeException e) {System.out.println(e);}
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

    public void selectIcon(View view) { throwRegisterDialog(); }

    private void throwRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione su imagen");
        builder.setPositiveButton("Cámara", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        });
        builder.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(Intent.createChooser(intent, "Seleccione su imagen"), 10);
            }
        });
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri path = data.getData();
            iconSelector.setImageURI(path);
        }

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            userImage= stream.toByteArray();
            iconSelector.setImageBitmap(imageBitmap);
        }
    }

    public void cargarODSUser(String nickname) {
        ODS_URepository ODS = new ODS_URepository(SingletonConnection.getSingletonInstance());
        for(int i = 0; i < 18; i++) {
            ODS.guardar(new ODS_has_User(nickname, i, 80, 20));
        }
    }

}
