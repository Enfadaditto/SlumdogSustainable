package Presentacion_layer;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import Domain_Layer.User;
import Persistence.UserRepository;

public class IUuserLogin extends AppCompatActivity {
    private Button logInButton;

    private Button registerButton;
    private Image background;
    private EditText usernameField;
    private EditText passwordField;
    private CheckBox showPassword;
    private TextView signupErrorText;
    private User userActual = new User("a", "b","c", null);

    public void registerOnClick(View view) {
        Intent intent = new Intent(IUuserLogin.this, IUuserRegister.class);
        startActivity(intent);
    }

    public void loginButtonOnClick(View view) {
        new Thread(new Runnable() {
            public void run(){
                try {
                    if (checkPassword()) {
                        MainActivity.user = new UserRepository(MainActivity.conexion).getUserByUsername(usernameField.getText().toString());
                        Intent intent = new Intent(IUuserLogin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        //signupErrorText.setVisibility(View.VISIBLE);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                PasswordError();
                                usernameField.getText().clear();
                                passwordField.getText().clear();
                            }
                        });
                    }
                }
                catch(Exception e){System.out.println(e);
                }
            }
        }).start();

    }

    public void PasswordError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Usuario o contraseña incorrecta")
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
    public boolean checkPassword() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        return new UserRepository(MainActivity.conexion).checkPassword(username, password);
    }

    public void passwordButton(View view) {
        if(passwordField.getTransformationMethod() == null) {
            passwordField.setTransformationMethod(new PasswordTransformationMethod());
        }

        else {
            passwordField.setTransformationMethod(null);
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // signupErrorText = findViewById(R.id.errorText);
        // signupErrorText.setVisibility(View.INVISIBLE);
        usernameField = findViewById(R.id.editTextTextPersonName3);
        passwordField = findViewById(R.id.editTextTextPassword);
        usernameField.setText("");
        passwordField.setText("");

        logInButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.RegisterButton);;

    }
}
