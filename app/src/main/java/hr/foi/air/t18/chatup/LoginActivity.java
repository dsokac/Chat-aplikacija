package hr.foi.air.t18.chatup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.LoginAsync;
import hr.foi.air.t18.webservice.RegisterAsync;

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

        //Find reference to btnForgotPassword and subscribe to onClick event
        TextView btnForgotPassword = (TextView)findViewById(R.id.loginForgottenPassword);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
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
                    if(currentClass.emailText.getText().toString().isEmpty()) {
                        currentClass.emailText.setError("E-mail is required!");
                    }

                    if(currentClass.passwordText.getText().toString().isEmpty()) {
                        currentClass.passwordText.setError("Password is required!");
                    }
                }
                else
                {
                    User user = new User();
                    user.setEmail(currentClass.emailText.getText().toString());
                    if(!user.validateEmail())
                    {
                        currentClass.emailText.setError("Invalid e-mail format!");
                    }
                    else Login(currentClass.emailText.getText().toString(), currentClass.passwordText.getText().toString(), currentClass);
                }
            }
        });
    }

    private void Login(String email, String password, final LoginActivity currentClass)
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

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                if(status == 0)
                {
                    finish();
                    Intent intent = new Intent(currentClass, MainClass.class);
                    startActivity(intent);
                }
                else
                {
                    currentClass.emailText.setText("");
                    currentClass.passwordText.setText("");
                }
            }
        });

        loginAsync.execute();
    }

}
