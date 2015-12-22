package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.HttpPOST;
import hr.foi.air.t18.core.Message;
import hr.foi.air.t18.core.User;

/**
 * Created by Danijel on 29.11.2015..
 */
public class FetchMessagesAsync extends AsyncTask<Void, Void, String>
{
    private User user1;
    private User user2;
    private IListener<Conversation> listener;

    public FetchMessagesAsync(User user1, User user2, IListener<Conversation> listener)
    {
        this.user1 = user1;
        this.user2 = user2;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        listener.onBegin();
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email1", user1.getEmail());
        parameters.put("email2", user2.getEmail());

        try {
            HttpPOST connection = new HttpPOST("http://104.236.58.50:8080/getMessages");
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
        Conversation data;

        try {
            JSONObject json = new JSONObject(result);
            message = json.getString("message");
            status = json.getInt("status");
            data = ConvertConversationsFromJSON(json.getString("data"));
        } catch (Exception e) {
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
            status = -1;
            data = null;
        }

        WebServiceResult<Conversation> wsResults = new WebServiceResult<>();
        wsResults.status = status;
        wsResults.message = message;
        wsResults.data = data;

        listener.onFinish(wsResults);
    }

    private Conversation ConvertConversationsFromJSON(String data) throws JSONException
    {
        Conversation conversation = new Conversation();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = new JSONArray(jsonObject.getString("messages"));

        conversation.addParticipant(user1);
        conversation.addParticipant(user2);
        conversation.setID(jsonObject.getString("id"));

        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonMessageObject = new JSONObject(jsonArray.getString(i));


            Message message = new Message(
                    jsonMessageObject.getString("text"),
                    jsonMessageObject.getString("sender"),
                    jsonMessageObject.getString("timeSend"),
                    jsonMessageObject.getString("location"),
                    jsonMessageObject.getString("type")
            );

            conversation.addMessage(message);
        }

        return conversation;
    }
}
