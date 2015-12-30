package hr.foi.air.t18.webservice;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by Jurman_Lap on 18.12.2015.
 */
public class AddFriendAsync extends AsyncTask<Void, Void, String> {

    private String email_prij;
    private String email_odab;
    private IListener<Void> listener;

    public AddFriendAsync(String email_prij,String email_odab, IListener<Void> listener) {
        this.email_prij = email_prij;
        this.email_odab = email_odab;
        this.listener = listener;
    }

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