package hr.foi.air.t18.webservice;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.HttpPOST;

/**
 * Created by Laptop on 18.12.2015..
 */
public class SaveImageAsync extends AsyncTask<Void, Void, String> {

    private String imageEncoded;
    private String mail;
    private IListener<Void> listener;

    /**
     * Constructor
     */
    public SaveImageAsync(String mail, String imageEncoded, IListener<Void> listener) {
        this.mail = mail;
        this.imageEncoded = imageEncoded;
        this.listener = listener;
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

    @Override
    protected String doInBackground(Void... params) {
        String response;

        HashMap<String, String> parameters = new HashMap<String, String>();

        parameters.put("mail", this.mail);
        parameters.put("picture", this.imageEncoded);

        try {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/saveProfilePic");
            connection.sendRequest(parameters);
            response = connection.getResponse();
            Log.d("response", response);
        } catch(Exception e) {
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
            e.printStackTrace();
            status = -1;
        }

        WebServiceResult<Void> wsResult = new WebServiceResult<Void>();
        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = null;

        this.listener.onFinish(wsResult);
    }
}
