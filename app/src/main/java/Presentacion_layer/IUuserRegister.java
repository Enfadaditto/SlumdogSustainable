package Presentacion_layer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.slumdogsustainable.R;
import Domain_Layer.User;
import Persistence.UserRepository;

public class IUuserRegister extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText nicknameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText repeatPasswordField;
    private ImageView iconSelector;
    private Button registerButton;
    private User userActual;



    @SuppressLint("MissingInflatedId")
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
        if (passwordField != repeatPasswordField) { return; /*errorLabel.setVisible(true)*/}
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
        System.out.println("ASDFASDFASDFADS");
    }

    /*
    public void selectIcon(View view) {
        Icon profileImage = throwRegisterDialog();
        iconSelector.setImageIcon(profileImage);
    }
    */

    //private Icon throwRegisterDialog() {
    public void selectIcon(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Select profile icon");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.out.println("CAMERA");
            }
        });
        builder.setNeutralButton("Storage", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.out.println("STORAGE");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.out.println("CANCEL");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        //return null;
    }

}
