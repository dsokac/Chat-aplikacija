package hr.foi.air.t18.core;

import android.os.AsyncTask;

import org.json.JSONObject;
import hr.foi.air.t18.webservice.HttpPOST;
import java.util.HashMap;

/**
 * Created by Goran on 1.11.2015..
 */
public class LogoutAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String password;
    private Listener listener;

    public LogoutAsync(String email, String password, Listener listener) {
        this.email = email;
        this.password = password;
        this.listener = listener;
    }

    public interface Listener
    {
        void onFinish(int status, String message);
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

        this.listener.onFinish(status, message);
    }
}
