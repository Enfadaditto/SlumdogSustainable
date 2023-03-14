package Presentacion_layer;

import com.google.android.material.resources.TextAppearanceConfig;
import com.google.android.material.textfield.TextInputLayout;
import com.slumdogsustainable.R;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Domain_Layer.User;
import Persistence.UserRepository;

public class IUuserLogin extends AppCompatActivity {
    // TODO: TODO AQUELLO QUE AHORA ESTE EN ROJO, UNA VEZ SE INTRODUZCAN LOS XML HABRA QUE RENOMBRARLOS

    Button logInButton = (Button) findViewById(R.id.loginButton);

    private Image background;
    private Image appIcon;
    private Image userImage;
    private Image passwordImage;

    private EditText usernameField;
    private EditText passwordField;
    private CheckBox showPassword;

    private TextView SignUp;
    private TextView signupErrorText;

    public View.OnClickListener signUpOnClick() {
        setContentView(R.layout.signup);
        return null;
    }

    public View.OnClickListener loginButtonOnClick() {
        if (checkPassword()) setContentView(R.layout.activity_main);
        else {
            signupErrorText.setVisibility(View.VISIBLE);
            usernameField.getText().clear();
            passwordField.getText().clear();
        }
        return null;
    }


    public boolean checkPassword() {
        String username = String.valueOf(usernameField.getText());
        String password = String.valueOf(passwordField.getText());
        return User.checkPassword(username, password);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        signupErrorText.setVisibility(View.INVISIBLE);
        usernameField.setPlaceholderText("username");
        passwordField.setPlaceholderText("password");

        SignUp = findViewById(R.id.signUp);
        SignUp.setText("Sign Up");
        SignUp.setOnClickListener(signUpOnClick());

        logInButton.setOnClickListener(loginButtonOnClick());
    }
}
