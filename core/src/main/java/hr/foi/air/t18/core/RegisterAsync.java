package hr.foi.air.t18.core;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;

/**
 * Created by Danijel on 27.10.2015..
 */
public class RegisterAsync extends AsyncTask<Void, Void, String>
{
    private User newUser;
    private String password;
    private Listener listener;

    public interface Listener
    {
        void onBegin();
        void onFinish(int status, String message);
    }

    public RegisterAsync(User newUser, String password, Listener listener)
    {
        this.newUser = newUser;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        this.listener.onBegin();
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("mail",this. newUser.getEmail());
        parameters.put("username", this.newUser.getUsername());
        parameters.put("gender", String.valueOf(this.newUser.getGender()));
        parameters.put("dateOfBirth", this.newUser.getDateOfBirth());
        parameters.put("password", this.password);

        try {
            HttpPOST connection = new HttpPOST("http://10.0.3.2:8080/register");
            connection.sendRequest(parameters);
            response = connection.getResponse();
        } catch(Exception e) {
            response = e.getMessage();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result)
    {
        String message;
        int status;

        try {
            JSONObject json = new JSONObject(result);
            message = json.getString("message");
            status = json.getInt("status");
        } catch (Exception e) {
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
            status = -1;
        }

        this.listener.onFinish(status, message);
    }
}
