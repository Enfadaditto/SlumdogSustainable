package Presentacion_layer;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import android.content.Intent;
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
    Button logInButton;
    private Image background;
    private EditText usernameField;
    private EditText passwordField;
    private CheckBox showPassword;
    private TextView registerText;
    private TextView signupErrorText;
    private User userActual = new User("a", "b","c");

    public void registerOnClick(View view) {
        Intent intent = new Intent(IUuserLogin.this, IUuserRegister.class);
        startActivity(intent);
    }

    public void loginButtonOnClick(View view) {
        if (checkPassword()) {
            Intent intent = new Intent(IUuserLogin.this, IUretoPregunta.class);
            startActivity(intent);
        }
        else {
            //signupErrorText.setVisibility(View.VISIBLE);
            usernameField.getText().clear();
            passwordField.getText().clear();
        }
    }


    public boolean checkPassword() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
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
