package hr.foi.air.t18.chatup.Menu;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.EditProfileAsync;
import hr.foi.air.t18.webservice.GetDataEditProfileAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Public class Edit profile implements logic of EditProfile activity
 */
public class EditProfile extends AppCompatActivity {

    final ArrayList<User> search = new ArrayList<User>();

    //current values of logged in user
    private String loggedIn2="undefined";
    private String editEmail="undefined";
    private String editUsername="undefined";
    private String editGender="undefined";
    private String selectedGender="undefined";
    private String editPassword="undefined";

    //new values (changed values)
    private String change_username="undefined_change";
    private String change_gender="undefined_change";
    private String change_password="undefined_change";
    private String change_password2="undefined_change";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get mail of logged in user
        if(MiddleMan.getObject() != null) {
            User user = (User)MiddleMan.getObject();
            loggedIn2 = user.getEmail();
        }

        //finding references
        final EditText editTextEditUsername = (EditText) findViewById(R.id.EditUsername);
        final TextView editTextEditEmail = (TextView) findViewById(R.id.YourEmail);
        final EditText editTextEditPassword = (EditText) findViewById(R.id.EditPassword);
        final EditText editTextEditPassword2 = (EditText) findViewById(R.id.EditPassword2);
        final TextView textViewGenderText=(TextView)findViewById(R.id.GenderText);
        final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.EditGender);
        Button btnSave = (Button) findViewById(R.id.btnEditSave);

        GetDataEditProfileAsync getDataEditProfileAsync = new GetDataEditProfileAsync(loggedIn2, new IListener<JSONArray>() {

            /***
             * Overridden onBegin event of GetDataEditProfileAsync task defines what is happening when async task starts to execute.
             */
            @Override
            public void onBegin() {
            }

            /***
             * Overridden onFinish event of GetDataEditProfileAsync task defines what happens when async task finish execution.
             * @param //status - status integer returned by web service
             * @param //message - message string returned by web service
             * @param //data - data returned by web service
             */
            @Override
            public void onFinish(WebServiceResult<JSONArray> result) {
                if (result.status == 0) {
                    try {
                        JSONArray json = result.data;

                        //adding json results in ArrayList<User> search
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject currentUser = json.getJSONObject(i);
                            search.add(new User(currentUser.getString("id"), currentUser.getString("username"), currentUser.getString("gender"), currentUser.getString("dateOfBirth"),currentUser.getString(("password"))));
                        }

                        //iterating through ArrayList search and search for email of logged in user
                        for (int i = 0; i < search.size(); i++) {
                            if(search.get(i).getEmail().equals(loggedIn2)) {
                                editEmail=search.get(i).getEmail();
                                editUsername=search.get(i).getUsername();
                                editGender=search.get(i).getGender();
                                editPassword=search.get(i).getPassword();
                            }
                            //setting hints
                            editTextEditUsername.setHint("user:'"+editUsername+"'");
                            editTextEditEmail.setText(editEmail);
                            editTextEditPassword.setHint("pass:'" + editPassword + "'");
                            editTextEditPassword2.setHint("Confirm new password");
                           //setting TextView next to RadioGroup
                            if(editGender.equals("M")){

                                textViewGenderText.setText("Male");
                            }
                            else
                            {
                                textViewGenderText.setText("Female");
                            }
                            //settting font (design)
                            editTextEditUsername.setTypeface(Typeface.SERIF);
                            textViewGenderText.setTypeface(Typeface.SERIF);
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

        //logic when user click on save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting up values of variables for change data
                change_username = editTextEditUsername.getText().toString();
                if(change_username.matches("")){
                    change_username=editUsername;
                }
                selectedGender=String.valueOf(genderRadioGroup.getCheckedRadioButtonId());

                if (selectedGender.equals("2131493004")){

                    change_gender="M";
                }
                else if (selectedGender.equals("2131493005")){
                    change_gender="Z";
                }
                else {
                    change_gender=editGender;
                }
                change_password=editTextEditPassword.getText().toString();
                if(change_password.matches("")){
                    change_password=editPassword;
                }
                change_password2=editTextEditPassword2.getText().toString();
                if(change_password2.matches("")){
                    change_password2=editPassword;
                }

                //if password are equals execute editProfileAsync
                    //note:(if you don't change values of any of them, there are still equal)
                    if (change_password.equals(change_password2)){
                    EditProfileAsync editProfileAsync = new EditProfileAsync(loggedIn2,change_username,change_gender,change_password, new IListener<Void>() {

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
                    //set values of EditText-s to null
                    editTextEditUsername.setText("");
                    editTextEditPassword.setText("");
                    editTextEditPassword2.setText("");
                        Toast.makeText(getApplicationContext(), "Update profile successfully.", Toast.LENGTH_SHORT).show();
                        Log.d(selectedGender,selectedGender);
                    }
                else{
                    Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}