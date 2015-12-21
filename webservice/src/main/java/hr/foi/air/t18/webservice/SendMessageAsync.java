package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.HttpPOST;
import hr.foi.air.t18.core.Message;

/**
 * Created by Danijel on 6.12.2015..
 */
public class SendMessageAsync extends AsyncTask<Void, Void, String>
{
    private Conversation conversation;
    private Message message;

    private IListener<Void> listener;

    public SendMessageAsync(Conversation conversation, Message message, IListener<Void> listener)
    {
        this.conversation = conversation;
        this.message = message;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", conversation.getID());
        parameters.put("sender", message.getSender());
        parameters.put("message", (String) message.getContent());
        parameters.put("location", message.getLocation());

        try {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/sendMessage");
            connection.sendRequest(parameters);
            response = connection.getResponse();
        } catch (Exception e)
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

        try {
            JSONObject json = new JSONObject(result);
            message = json.getString("message");
            status = json.getInt("status");
        } catch (Exception e) {
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
            status = -1;
        }

        WebServiceResult<Void> wsResults = new WebServiceResult<>();
        wsResults.status = status;
        wsResults.message = message;
        wsResults.data = null;

        listener.onFinish(wsResults);
    }
}
