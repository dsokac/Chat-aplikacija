package hr.foi.air.t18.webservice.LoginAsync;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;
import hr.foi.air.t18.webservice.WebServiceStrings;

/**
 * This class is used to communicate with database through web service. The class purpose is to
 * log in the app user.
 * The class creates HTTP request with e-mail and password as parameters, it sends the request to
 * the web service and waits for its response.
 *
 * Created by Danijel on 30.10.2015..
 */
public class LoginAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String password;
    private IListener<Void> listener;

    /**
     * The class constructor assigns values to the class private variables.
     * @param email - user's unique email
     * @param password - user's password
     * @param listener - listener with implemented onBegin() and onFinish() events
     */
    public LoginAsync(String email, String password, IListener<Void> listener)
    {
        this.password = password;
        this.email = email;
        this.listener = listener;
    }

    /***
     * Overridden function that runs listener's onBegin event.
     * onPreExecute is run before execution of Async task.
     */
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

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mail",this.email);
        parameters.put("password",this.password);

        try
        {
            HttpPOST connection = new HttpPOST(WebServiceStrings.SERVER + WebServiceStrings.LOGIN);
            connection.sendRequest(parameters);
            response = connection.getResponse();
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
