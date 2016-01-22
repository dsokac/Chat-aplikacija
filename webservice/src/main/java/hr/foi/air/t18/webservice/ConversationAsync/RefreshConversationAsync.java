package hr.foi.air.t18.webservice.ConversationAsync;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import hr.foi.air.t18.core.Message;
import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;
import hr.foi.air.t18.webservice.WebServiceStrings;

/**
 * AsyncTask class that refreshes the active conversation by contacting
 * the Web service for new messages.
 *
 * Created by Danijel on 24.12.2015..
 */
public class RefreshConversationAsync extends AsyncTask<Void, Void, String>
{
    String chatID;
    String timestamp;
    IListener<ArrayList<Message>> listener;

    /**
     * Constructor for RefreshConversationAsync class.
     * @param chatID ID of active conversation
     * @param timestamp Timestamp of the latest message
     * @param listener IListener object that implements onBegin() and onFinish() methods
     */
    public RefreshConversationAsync(String chatID, String timestamp, IListener<ArrayList<Message>> listener)
    {
        this.chatID = chatID;
        this.timestamp = timestamp;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("chatID", chatID);
        parameters.put("timestamp", timestamp);

        try
        {
            HttpPOST connection = new HttpPOST(WebServiceStrings.SERVER + WebServiceStrings.GET_NEW_MESSAGES);
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
        ArrayList<Message> data;

        try
        {
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");
            data = status == 0
                    ? GetNewMessages(jsonObject.getString("data"))
                    : null;
        }
        catch (Exception e)
        {
            status = -1;
            message = e.getMessage();
            data = null;
        }

        WebServiceResult<ArrayList<Message>> wsResult = new WebServiceResult<>();
        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = data;

        listener.onFinish(wsResult);
    }

    private ArrayList<Message> GetNewMessages(String data) throws JSONException
    {
        ArrayList<Message> messages = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
            Message message = new Message(
                    jsonObject.getString("text"),
                    jsonObject.getString("sender"),
                    jsonObject.getString("timeSend"),
                    jsonObject.getString("location"),
                    jsonObject.getString("type")
            );
            messages.add(message);
        }

        return messages;
    }
}
