package hr.foi.air.t18.webservice.MainAsync;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;
import hr.foi.air.t18.webservice.WebServiceStrings;

/**
 * Created by Danijel on 8.12.2015..
 */
public class FriendsAsync extends AsyncTask<Void, Void, String> {

    private IListener listener;
    private String email;

    public FriendsAsync(String email,IListener listener)
    {
        this.listener = listener;
        this.email = email;
    }

    @Override
    protected void onPreExecute() {
        this.listener.onBegin();
    }

    @Override
    protected String doInBackground(Void... params) {
        String response;

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("id",this.email);

        try
        {
            HttpPOST connection = new HttpPOST(WebServiceStrings.SERVER + WebServiceStrings.GET_FRIENDS);
            connection.sendRequest(parameters);
            response = connection.getResponse();
        }
        catch (Exception e)
        {
            response = e.getMessage();
        }
        return  response;
    }

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
