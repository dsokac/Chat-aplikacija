package hr.foi.air.t18.webservice;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.HttpPOST;

/**
 * Created by Goran on 1.11.2015..
 */
public class LogoutAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String password;
    private IListener<Void> listener;

    public LogoutAsync(String email, String password, IListener<Void> listener) {
        this.email = email;
        this.password = password;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mail",this.email);
        parameters.put("password",this.password);
        try
        {
            HttpPOST connection = new HttpPOST("http://10.0.3.2:8080/logout");
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

        WebServiceResult<Void> wsResult = new WebServiceResult<Void>();
        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = null;

        this.listener.onFinish(wsResult);
    }
}