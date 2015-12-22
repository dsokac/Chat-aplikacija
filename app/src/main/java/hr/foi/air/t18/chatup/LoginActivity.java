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

import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.FetchUserDataAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.LoginAsync;
import hr.foi.air.t18.webservice.RegisterAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

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

        //Find reference to loginAdminLoginButton and subscribe to onClick event
        Button btnloginAdminLoginButton = (Button)findViewById(R.id.loginAdminLoginButton);
        btnloginAdminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainClass.class);
                User user = new User();
                user.setEmail("dsokac@foi.hr");
                MiddleMan.setObject(user);
                startActivity(i);
            }
        });
    }

    /***
     * Function getControls() gets activity elements and adds them to class variables
     */
    private void getControls()
    {
         this.emailText = (EditText) findViewById(R.id.loginEmail);
         this.passwordText = (EditText) findViewById(R.id.loginPassword);
         this.btnRegister = (Button) findViewById(R.id.loginRegisterButton);
         this.btnLogin = (Button) findViewById(R.id.loginLoginButton);
    }


    /**
     * Function setEvents() sets button events. Register button opens new activity.
     * Login button launches event to check if email and password are written and if
     * email has correct format. If everything is fine it runs Login function.
     */
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

    /***
     * Function Login() runs login async task and implements onBegin and onFinish events
     * @param email - user's email
     * @param password - user's password
     * @param currentClass - current active activity class
     */
    private void Login(final String email, String password, final LoginActivity currentClass)
        {
        LoginAsync loginAsync = new LoginAsync(email, password, new IListener<Void>() {
            /***
             * Overridden onBegin event of LoginAsync task defines what is happening when async task starts to execute.
             * It displays message 'Signing in...'.
             */
            @Override
            public void onBegin() {
                progress.setMessage("Signing in...");
                progress.show();
            }

            /***
             * Overridden onFinish event of LoginAsync task defines what happens when async task finish execution.
             * The event closes progress dialog and displays message returned by web service.
             * If everything is corrent it opens MainClass activity, otherwise it empties text inputs.
             * @param //status - status integer returned by web service
             * @param //message - message string returned by web service
             */
            @Override
            public void onFinish(WebServiceResult<Void> wsResult) {
                if(progress.isShowing()) progress.dismiss();

                Toast.makeText(getApplicationContext(), wsResult.message, Toast.LENGTH_LONG).show();
                if(wsResult.status == 0)
                {
                    finish();
                    Intent intent = new Intent(currentClass, MainClass.class);
                    User user = new User();
                    user.setEmail(email);
                    MiddleMan.setObject(user);

                    fetchLoggedUserData(email);

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

    private void fetchLoggedUserData(String email)
    {
        FetchUserDataAsync fud = new FetchUserDataAsync(email, new IListener<User>()
        {
            @Override
            public void onBegin() {}

            @Override
            public void onFinish(WebServiceResult<User> result)
            {

            }
        });
        fud.execute();
    }
}
