package Presentacion_layer;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class IUuserLogin extends AppCompatActivity {
    private Button logInButton;

    private Button registerButton;
    private Image background;
    private EditText usernameField;
    private EditText passwordField;
    private CheckBox showPassword;
    private TextView signupErrorText;
    private final User userActual = new User("a", "b","c", 0);

    public void registerOnClick(View view) {
        Intent intent = new Intent(IUuserLogin.this, IUuserRegister.class);
        startActivity(intent);
    }

    public void loginButtonOnClick(View view) {
        setContentView(R.layout.pantalla_carga);
        new Thread(new Runnable() {
            public void run(){
                try {
                    if (checkPassword()) {
                        MainActivity.user = User.getUserByUsername(new UserRepository(SingletonConnection.getSingletonInstance()), usernameField.getText().toString());
                        Intent intent = new Intent(IUuserLogin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        //signupErrorText.setVisibility(View.VISIBLE);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                setContentView(R.layout.login);
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
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public boolean checkPassword() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        return User.checkPassword(new UserRepository(SingletonConnection.getSingletonInstance()), username, password);
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // signupErrorText = findViewById(R.id.errorText);
        // signupErrorText.setVisibility(View.INVISIBLE);
        usernameField = findViewById(R.id.editTextTextPersonName3);
        passwordField = findViewById(R.id.editTextTextPassword);
        usernameField.setText("");
        passwordField.setText("");

        logInButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.RegisterButton);

    }
}
