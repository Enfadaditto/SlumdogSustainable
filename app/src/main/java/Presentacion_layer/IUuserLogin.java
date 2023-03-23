package Presentacion_layer;

import com.slumdogsustainable.R;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Domain_Layer.User;

public class IUuserLogin extends AppCompatActivity {
    // TODO: TODO AQUELLO QUE AHORA ESTE EN ROJO, UNA VEZ SE INTRODUZCAN LOS XML HABRA QUE RENOMBRARLOS

    Button logInButton;

    private Image background;

    private EditText usernameField;
    private EditText passwordField;
    private CheckBox showPassword;

    private TextView registerText;
    private TextView signupErrorText;
    private User userActual;

    public void registerOnClick() {
        setContentView(R.layout.register);
    }

    public void loginButtonOnClick() {
        if (checkPassword()) setContentView(R.layout.activity_main);
        else {
            signupErrorText.setVisibility(View.VISIBLE);
            usernameField.getText().clear();
            passwordField.getText().clear();
        }
    }


    public boolean checkPassword() {
        String username = String.valueOf(usernameField.getText());
        String password = String.valueOf(passwordField.getText());
        return this.userActual.checkPassword(username, password);
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

        logInButton = (Button) findViewById(R.id.loginButton);
        registerText = (TextView) findViewById(R.id.register);;
    }
}
