package hr.foi.air.t18.chatup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import hr.foi.air.t18.core.LoginAsync;
import hr.foi.air.t18.core.RegisterAsync;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private Button btnRegister;
    private Button btnLogin;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getControls();
        this.progress = new ProgressDialog(this);
        setEvents();
    }

    private void getControls()
    {
         this.emailText = (EditText) findViewById(R.id.loginEmail);
         this.passwordText = (EditText) findViewById(R.id.loginPassword);
         this.btnRegister = (Button) findViewById(R.id.loginRegisterButton);
         this.btnLogin = (Button) findViewById(R.id.loginLoginButton);
    }


    private void setEvents() {
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        final LoginActivity currentClass = this;
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentClass.emailText.getText().toString().isEmpty() || currentClass.passwordText.getText().toString().isEmpty())
                {
                    if(currentClass.emailText.getText().toString().isEmpty())
                        currentClass.emailText.setError("E-mail is required!");
                    if(currentClass.passwordText.getText().toString().isEmpty())
                        currentClass.passwordText.setError("Password is required!");
                    currentClass.emailText.setText("");
                    currentClass.passwordText.setText("");
                }
                else
                {
                    Login(currentClass.emailText.getText().toString(), currentClass.passwordText.getText().toString());
                }
            }
        });
    }

    private void Login(String email, String password)
    {
        LoginAsync loginAsync = new LoginAsync(email, password, new RegisterAsync.Listener() {
            @Override
            public void onBegin() {
                progress.setMessage("Signing in...");
                progress.show();
            }

            @Override
            public void onFinish(int status, String message) {
                if(progress.isShowing()) progress.dismiss();

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                if(status == 0) finish();
            }
        });

        loginAsync.execute();
    }

}
