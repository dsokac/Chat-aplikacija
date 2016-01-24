package hr.foi.air.t18.chatup.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import hr.foi.air.t18.chatup.Mail.GMailSender;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.LoginAsync.ForgotPasswordAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;


/**
 * Public class ForgotPasswordActivity implements logic of Forgot Password Activity
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    //value of entered data
    private String forgot_entered;

    //value of password which is forgotten
    private String forgot_password;

    //all users and users informations
    final ArrayList<User> forgot_users = new ArrayList<User>();

    private Context ctx;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this.getApplicationContext();
        this.activity = this;
        setContentView(R.layout.activity_forgot_password);
        final EditText forgotEmail = (EditText) findViewById(R.id.forgotUnos);
        final Button forgotSend = (Button) findViewById(R.id.forgotSendButton);

        //logic when user click on button Send (OnClickListener)
        forgotSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_entered = forgotEmail.getText().toString();

                //check if empty e-mail is sent , if yes it shows alert with warning
                if(forgot_entered.contentEquals(""))
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                            .setTitle(R.string.ConversationDialogTitle)
                            .setMessage(R.string.ForgotPassEmptyDialogContent)
                            .setNeutralButton(R.string.ConversationDialogButtonText, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                    return;
                }
                ForgotPasswordAsync forgotPasswordAsync = new ForgotPasswordAsync(forgot_entered, new IListener<JSONArray>() {

                    /***
                     * Overridden onBegin event of ForgotPasswordAsync task defines what is happening when async task starts to execute.
                     */
                    @Override
                    public void onBegin() {
                    }
                    /***
                     * Overridden onFinish event of ForgotPasswordAsync task defines what happens when async task finish execution.
                     * @param //status - status integer returned by web service
                     * @param //message - message string returned by web service
                     * @param //data - data returned by web service
                     */
                    @Override
                    public void onFinish(WebServiceResult<JSONArray> result) {
                        if (result.status == 0) {
                            try {
                                JSONArray json = result.data;
                                //adding json results in ArrayList<User> forgot_users
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject currentUser = json.getJSONObject(i);
                                    forgot_users.add(new User(currentUser.getString("id"), currentUser.getString("username"), currentUser.getString("gender"), currentUser.getString("dateOfBirth"), currentUser.getString("password")));
                                }

                                //iterating through ArrayList forgot_users and compare entered value and mail value of current user
                                for (int i = 0; i < forgot_users.size(); i++) {
                                    if (forgot_users.get(i).getEmail().equals(forgot_entered)) {
                                        //get password for user
                                        forgot_password = forgot_users.get(i).getPassword();
                                        //send mail
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    GMailSender sender = new GMailSender("matija.jurman@gmail.com",
                                                            "tipkovnica");
                                                    sender.sendMail("ChatUP forgotten password", "Dear " + forgot_entered + ",\nyour ChatUP password is: '" + forgot_password + "'",
                                                            "matija.jurman@gmail.com", forgot_entered);
                                                } catch (Exception e) {
                                                    Log.e("SendMail", e.getMessage(), e);
                                                }
                                            }
                                        }).start();
                                        //Toast.makeText(getApplicationContext(), "Successfully sended mail with instructions.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                forgotPasswordAsync.execute();
                forgot_password = "";

                //Inform that e-mail is sent and destroyes the activity
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                        .setTitle(R.string.ForgotPassDialogTitle)
                        .setMessage(getString(R.string.ForgotPassDialogContent)+forgot_entered)
                        .setNeutralButton(R.string.ConversationDialogButtonText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ForgotPasswordActivity.this.finish();
                            }
                        });
                alertDialog.show();
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
