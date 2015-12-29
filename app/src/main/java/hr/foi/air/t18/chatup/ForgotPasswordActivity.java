package hr.foi.air.t18.chatup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.Mail.GMailSender;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.ForgotPasswordAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;


public class ForgotPasswordActivity extends AppCompatActivity {
    private String forgot_entered;
    private String forgot_password;
    final ArrayList<User> forgot_users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        final EditText forgotEmail = (EditText) findViewById(R.id.forgotUnos);
        final Button forgotSend = (Button) findViewById(R.id.forgotSendButton);

        forgotSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_entered = forgotEmail.getText().toString();

                ForgotPasswordAsync forgotPasswordAsync = new ForgotPasswordAsync(forgot_entered, new IListener<JSONArray>() {
                    @Override
                    public void onBegin() {
                    }

                    @Override
                    public void onFinish(WebServiceResult<JSONArray> result) {
                        if (result.status == 0) {
                            try {
                                JSONArray json = result.data;
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject currentUser = json.getJSONObject(i);
                                    forgot_users.add(new User(currentUser.getString("id"), currentUser.getString("username"), currentUser.getString("gender"), currentUser.getString("dateOfBirth"), currentUser.getString("password")));
                                }

                                for (int i = 0; i < forgot_users.size(); i++) {
                                    if (forgot_users.get(i).getEmail().equals(forgot_entered)) {
                                        forgot_password = forgot_users.get(i).getPassword();
                                        //Log.d("procitano:"+forgot_users.get(i).getEmail()+" "+forgot_users.get(i).getPassword()," ;uneseno:" +forgot_entered);
                                        //Toast.makeText(getApplicationContext(), forgot_password, Toast.LENGTH_SHORT).show();
                                        //ovdje ide logika za slanje maila

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
            }

        });
    }
}
