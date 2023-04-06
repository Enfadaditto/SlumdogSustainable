package Presentacion_layer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.slumdogsustainable.R;
import java.io.File;

import Domain_Layer.User;
import Persistence.UserRepository;

public class IUuserRegister extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nicknameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText repeatPasswordField;
    private ImageView iconSelector;
    private Button registerButton;
    private User userActual;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        nicknameField = findViewById(R.id.nicknameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        iconSelector = findViewById(R.id.iconSelector);
        registerButton = findViewById(R.id.registerButton);
    }

    public void registerButtonOnClick(View view) {
        if (passwordField != repeatPasswordField) {
            PasswordError();
            return;
            /*errorLabel.setVisible(true)*/}
        if (userActual.checkUsernameNotTaken(nicknameField.getText().toString())
            && userActual.passwordIsSafe(passwordField.getText().toString())) {
            new UserRepository().guardar(
                    new User(
                            nicknameField.getText().toString(),
                            emailField.getText().toString(),
                            passwordField.getText().toString()
                    )
            );
        };
        //enviar a pantalla de juego principal
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

    public void selectIcon(View view) { throwRegisterDialog(); }

    private void throwRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select profile icon");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.out.println("CAMERA");
            }
        });
        builder.setNegativeButton("Storage", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            File image = new File(String.valueOf(uri));
            System.out.println("ESTO ES LA URI:  " + uri.toString());
            iconSelector.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
        }
    }

}
