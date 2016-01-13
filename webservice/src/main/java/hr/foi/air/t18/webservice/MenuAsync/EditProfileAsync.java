package hr.foi.air.t18.webservice.MenuAsync;


import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * This class is used to communicate with database through web service. The class purpose is to
 * edit user profile (username, gender, and password).
 * The class creates HTTP request with email, username, gender and password as parameters, it sends the request to
 * the web service and waits for its response.
 * Created by Jurman_Lap on 23.12.2015..
 */
public class EditProfileAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String username;
    private String gender;
    private String password;
    private IListener<Void> listener;

    /**
     * The class constructor assigns values to the class private variables.
     */
    public EditProfileAsync(String email,String username,String gender, String password,  IListener<Void> listener) {
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.password = password;
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
        parameters.put("mail", this.email);
        parameters.put("mail2",this.username);
        parameters.put("mail3",this.gender);
        parameters.put("mail4",this.password);

        try
        {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/editProfile");
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
     * Overridden async task's function onPostExecute() runs after request sending and it gets
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