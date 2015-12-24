package hr.foi.air.t18.webservice;


import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;
import hr.foi.air.t18.core.HttpPOST;

/**
 * Created by Jurman_Lap on 23.12.2015..
 */
public class EditProfileAsync extends AsyncTask<Void, Void, String> {

    private String email;
    private String username;
    private IListener<Void> listener;

    public EditProfileAsync(String email,String username,  IListener<Void> listener) {
        this.email = email;
        this.username = username;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mail", this.email);
        parameters.put("mail2",this.username);
        //Log.d("mail", this.email);
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