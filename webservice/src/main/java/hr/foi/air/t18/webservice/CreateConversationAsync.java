package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.HttpPOST;

/**
 * Created by Danijel on 23.12.2015..
 */
public class CreateConversationAsync extends AsyncTask<Void, Void, String>
{
    private String email1;
    private String email2;
    private IListener<Void> listener;

    public CreateConversationAsync(String email1, String email2, IListener<Void> listener)
    {
        this.email1 = email1;
        this.email2 = email2;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mail1", email1);
        parameters.put("mail2", email2);

        try
        {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/createConversation");
            connection.sendRequest(parameters);
            response = connection.getResponse();
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
        int status;
        String message;

        try
        {
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");
        }
        catch (Exception e)
        {
            status = -1;
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
        }

        WebServiceResult wsResult = new WebServiceResult();
        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = null;

        listener.onFinish(wsResult);
    }
}
