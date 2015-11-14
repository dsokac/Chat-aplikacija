package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.HttpPOST;

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
    private RegisterAsync.Listener listener;

    /**
     * The class constructor assigns values to the class private variables.
     * @param email - user's unique email
     * @param password - user's password
     * @param listener - listener with implemented onBegin() and onFinish() events
     */
    public LoginAsync(String email, String password, RegisterAsync.Listener listener)
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

        this.listener.onFinish(status, message);
    }
}
