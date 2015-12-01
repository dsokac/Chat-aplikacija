package hr.foi.air.t18.webservice;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.util.HashMap;
import hr.foi.air.t18.core.HttpPOST;

/**
 * This class is used to communicate with database through web service. The class purpose is to
 * get e-mail and username of registered users in database.
 * The class creates HTTP request with e-mail and username  as parameters, it sends the request to
 * the web service and waits for its response.
 * Created by JurmanLap on 19.11.2015..
 */
public class SearchAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String username;
    private IListener<Void> listener;

    public SearchAsync(String email, String username, IListener<Void> listener)
    {
        this.username = username;
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
        parameters.put("username",this.username);

        try
        {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/search");
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

        WebServiceResult<Void> wsResult = new WebServiceResult<Void>();
        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = null;

        this.listener.onFinish(wsResult);
    }
}