package hr.foi.air.t18.chatup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.EditProfileAsync;
import hr.foi.air.t18.webservice.GetDataEditProfileAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.LogoutAsync;
import hr.foi.air.t18.webservice.SearchAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

public class EditProfile extends AppCompatActivity {
    final ArrayList<User> search = new ArrayList<User>();
    private String loggedIn2="undefined";
    private String editEmail="undefined";
    private String editUsername="undefined";
    private String editGender="undefined";
    private String editPassword="undefined";
    public String change_username="undefined_change";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        if(MiddleMan.getObject() != null) {
            User user = (User)MiddleMan.getObject();
            loggedIn2 = user.getEmail();
        }

        final EditText editTextEditUsername = (EditText) findViewById(R.id.EditUsername);
        final EditText editTextEditEmail = (EditText) findViewById(R.id.EditMail);
        final EditText editTextEditPassword = (EditText) findViewById(R.id.EditPassword);
        final EditText editTextEditPassword2 = (EditText) findViewById(R.id.EditPassword2);
        Button btnSave = (Button) findViewById(R.id.btnEditSave);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetDataEditProfileAsync getDataEditProfileAsync = new GetDataEditProfileAsync(loggedIn2, new IListener<JSONArray>() {
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
                            search.add(new User(currentUser.getString("id"), currentUser.getString("username"), currentUser.getString("gender"), currentUser.getString("dateOfBirth"),currentUser.getString(("password"))));

                        }

                        for (int i = 0; i < search.size(); i++) {
                            if(search.get(i).getEmail().equals(loggedIn2)) {
                                editEmail=search.get(i).getEmail();
                                editUsername=search.get(i).getUsername();
                                editGender=search.get(i).getGender();
                                editPassword=search.get(i).getPassword();
                            }
                            editTextEditUsername.setHint("user:'"+editUsername+"'");
                            editTextEditEmail.setHint("mail:'"+editEmail+"'");
                            editTextEditPassword.setHint("pass:'"+editPassword+"'");
                            editTextEditPassword2.setHint("Confirm new password");
                            editTextEditUsername.setTypeface(Typeface.SERIF);
                            editTextEditEmail.setTypeface(Typeface.SERIF);
                            editTextEditPassword.setTypeface(Typeface.SERIF);
                            editTextEditPassword2.setTypeface(Typeface.SERIF);
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_LONG).show();
                }
            }
        });
        getDataEditProfileAsync.execute();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_username=editTextEditUsername.getText().toString();
                EditProfileAsync editProfileAsync = new EditProfileAsync(loggedIn2,change_username, new IListener<Void>() {

                    @Override
                    public void onBegin() {
                    }

                    @Override
                    public void onFinish(WebServiceResult<Void> wsResult) {
                        if(wsResult.status == 0)
                        {
                        }
                    }
                });
                editProfileAsync.execute();
                editTextEditUsername.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_home:
                Intent intent = new Intent(getApplicationContext(), MainClass.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}