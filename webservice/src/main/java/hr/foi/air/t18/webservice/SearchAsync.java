package hr.foi.air.t18.webservice;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * This class is used to communicate with database through web service. The class purpose is to
 * fetch all registered users
 * The class creates HTTP request with email as parameters, it sends the request to
 * the web service and waits for its response.
 * Created by Jurman_Lap on 19.11.2015.
 */
public class SearchAsync extends AsyncTask<Void, Void, String> {

    private IListener listener;
    private String email;

    /**
     * The class constructor assigns values to the class private variables.
     */
    public SearchAsync(String email,IListener listener)
    {
        this.listener = listener;
        this.email = email;
    }

    @Override
    protected void onPreExecute() {
        this.listener.onBegin();
    }

    /***
     * Async task's function which is running in background and it is used to create HTTP parameters
     * and HTTP request. It sends request to the web service for log in and waits for response.
     * @param params
     * @return response - Web service's response
     */
    @Override
    protected String doInBackground(Void... params) {
        String response;

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("id",this.email);

        try
        {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/registeredUsers2");
            connection.sendRequest(parameters);
            response = connection.getResponse();
        }
        catch (Exception e)
        {
            response = e.getMessage();
        }
        return  response;
    }

    /***
     * Overridden async task's function onPostExecute() runs after request sending and it gets
     * web service's response as JSON object and parses the JSON to extract message and status
     * returned by web service. The function runs onFinish event.
     * @param result - JSON object containing message and status returned by web service
     */
    @Override
    protected void onPostExecute(String result) {
        WebServiceResult<JSONArray> response = new WebServiceResult<>();
        try
        {
            JSONObject json = new JSONObject(result);
            response.message = json.getString("message");
            response.status = json.getInt("status");
            response.data = json.getJSONArray("data");
        }
        catch (Exception e)
        {
            response.message = "Error parsing JSON. Either JSON is invalid or server is down.";
            response.status = -1;
        }

        this.listener.onFinish(response);

    }
}
