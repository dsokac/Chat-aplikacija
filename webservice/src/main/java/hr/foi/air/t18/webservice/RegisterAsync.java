package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.HttpPOST;
import hr.foi.air.t18.core.User;

/**
 * This class is used to start an async task which will contact the
 * Web service to start a registration process of the user.
 * Created by Danijel on 27.10.2015..
 */
public class RegisterAsync extends AsyncTask<Void, Void, String>
{
    private User newUser;
    private String password;
    private Listener listener;

    /**
     * Interface for a Listener that will have implemented onBegin()
     * and onFinish() methods
     */
    public interface Listener
    {
        void onBegin();
        void onFinish(int status, String message);
    }

    /**
     * Constructor for the RegisterAsync class.
     * @param newUser User object
     * @param password String that contains the inputted password
     * @param listener Listener that has implemented onBegin() and onFinish() events
     */
    public RegisterAsync(User newUser, String password, Listener listener)
    {
        this.newUser = newUser;
        this.listener = listener;
        this.password = password;
    }

    /**
     * Overridden onPreExecute() method which calls
     * implemented onBegin() event.
     */
    @Override
    protected void onPreExecute()
    {
        this.listener.onBegin();
    }

    /**
     * Overridden doInBackground() method which contacts the Web server
     * for registration.
     * @param params
     * @return Web server response
     */
    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("mail", this.newUser.getEmail());
        parameters.put("username", this.newUser.getUsername());
        parameters.put("gender", String.valueOf(this.newUser.getGender()));
        parameters.put("dateOfBirth", this.newUser.getDateOfBirth());
        parameters.put("password", this.password);

        try {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/register");
            connection.sendRequest(parameters);
            response = connection.getResponse();
        } catch(Exception e) {
            response = e.getMessage();
        }

        return response;
    }

    /**
     * Overridden onPostExecute() method that parser JSON
     * response and calls onFinish() event.
     * @param result
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

        this.listener.onFinish(status, message);
    }
}

