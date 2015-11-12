package hr.foi.air.t18.chatup;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.RegisterAsync;

public class RegisterActivity extends AppCompatActivity
{
    private EditText editEmail;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editPassword2;
    private RadioGroup radioGender;
    private DatePicker dpDateOfBirth;
    private Button buttonOK;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();
        addEvents();
    }

    /**
     * Initializes controls.
     */
    private void initializeComponents()
    {
        this.editEmail = (EditText) findViewById(R.id.regEmail);
        this.editUsername = (EditText) findViewById(R.id.regUsername);
        this.editPassword = (EditText) findViewById(R.id.regPassword);
        this.editPassword2 = (EditText) findViewById(R.id.regPassword2);
        this.radioGender = (RadioGroup) findViewById(R.id.regRadioGroup);
        this.dpDateOfBirth = (DatePicker) findViewById(R.id.regDatePicker);
        this.buttonOK = (Button) findViewById(R.id.btnRegOk);
        this.progress = new ProgressDialog(this);
    }

    /**
     * Adds onClick() event to the OK button.
     */
    private void addEvents()
    {
        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = buildUser();
                String password1 = editPassword.getText().toString();
                String password2 = editPassword2.getText().toString();
                if (newUser.validateEmail() && newUser.validatePassword(password1, password2)) {
                    registerUser(newUser, password1);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Builds User object out of user's input.
     * @return User object
     */
    private User buildUser()
    {
        String email = editEmail.getText().toString();
        String username = editUsername.getText().toString();
        char gender = radioGender.getCheckedRadioButtonId() == R.id.radioMale
                ? 'M'
                : 'Z';
        String dateOfBirth = Integer.toString(dpDateOfBirth.getYear()) + "-"
                + Integer.toString(dpDateOfBirth.getMonth()) + "-"
                + Integer.toString(dpDateOfBirth.getDayOfMonth());

        User newUser = new User(email, username, gender, dateOfBirth);
        return newUser;
    }

    /**
     * Runs async task for the registration of the new user.
     * @param newUser New User object
     * @param password1 User's password
     */
    private void registerUser(User newUser, String password1)
    {
        RegisterAsync process = new RegisterAsync(newUser, password1, new RegisterAsync.Listener() {
            @Override
            public void onBegin() {
                progress.setMessage("Registering...");
                progress.show();
            }

            @Override
            public void onFinish(int status, String message) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                if (status == 0) {
                    finish();
                }
            }
        });
        process.execute();
    }
}
