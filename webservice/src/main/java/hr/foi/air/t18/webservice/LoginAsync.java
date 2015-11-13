package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.HttpPOST;

/**
 * Created by Danijel on 30.10.2015..
 */
public class LoginAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String password;
    private RegisterAsync.Listener listener;

    public LoginAsync(String email, String password, RegisterAsync.Listener listener)
    {
        this.password = password;
        this.email = email;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        this.listener.onBegin();
    }

    @Override
    protected String doInBackground(Void... params) {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mail",this.email);
        parameters.put("password",this.password);

        try
        {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/login");
            connection.sendRequest(parameters);
            response = connection.getResponse();
        }
        catch (Exception e)
        {
            response = e.getMessage();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        String message;
        int status;

        try
        {
            JSONObject json = new JSONObject(result);
            message = json.getString("message");
            status = json.getInt("status");

        }
        catch (Exception e)
        {
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
            status = -1;
        }

        this.listener.onFinish(status, message);
    }
}
