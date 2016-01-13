package hr.foi.air.t18.webservice.MainAsync;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * This class is used to communicate with database through web service. The class purpose is to
 * add friend to logged-in user and add friend to selected user.
 * The class creates HTTP request with email_prij and email_odab as parameters, it sends the request to
 * the web service and waits for its response.
 * Created by Jurman_Lap on 18.12.2015.
 */
public class AddFriendAsync extends AsyncTask<Void, Void, String> {

    private String email_prij;
    private String email_odab;
    private IListener<Void> listener;

    /**
     * The class constructor assigns values to the class private variables.
     * @param email_prij - logged in user
     * @param email_odab - selected user
     * @param listener - listener with implemented onBegin() and onFinish() events
     */
    public AddFriendAsync(String email_prij,String email_odab, IListener<Void> listener) {
        this.email_prij = email_prij;
        this.email_odab = email_odab;
        this.listener = listener;
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

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mail1",this.email_prij);
        parameters.put("mail2",this.email_odab);

        Log.d("mail prij", this.email_prij);
        Log.d("mail odabrani", this.email_odab);

        try
        {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/addFriends");
            connection.sendRequest(parameters);
            response = connection.getResponse();
            Log.d("response", response);
        }
        catch (Exception e)
        {
            response = e.getMessage();
        }

        return response;
    }
    /***
     * Overridden async task's function onPost Execute() runs after request sending and it gets
     * web service's response as JSON object and parses the JSON to extract message and status
     * returned by web service. The function runs onFinish event.
     * @param result - JSON object containing message and status returned by web service
     */
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