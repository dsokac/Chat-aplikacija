package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.Message;

/**
 * An AsyncTask class that sends a message to be stored in a conversation.
 * Created by Danijel on 6.12.2015..
 */
public class SendMessageAsync extends AsyncTask<Void, Void, String>
{
    private Conversation conversation;
    private Message message;

    private IListener<Message> listener;

    /**
     * Constructor for SendMessageAsync class.
     * @param conversation Conversation object
     * @param message Message object
     * @param listener IListener object that has implemented onBegin() and onFinish() methods
     */
    public SendMessageAsync(Conversation conversation, Message message, IListener<Message> listener)
    {
        this.conversation = conversation;
        this.message = message;
        this.listener = listener;
    }

    /**
     * Overriden doInBackground() which contacts the Web service for storing
     * sent messages.
     * @param params
     * @return Web server response
     */
    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", conversation.getID());
        parameters.put("sender", message.getSender());
        parameters.put("message", message.getContent());
        parameters.put("location", message.getLocation());
        parameters.put("type", message.getType());

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
        Message data;

        try {
            JSONObject json = new JSONObject(result);
            message = json.getString("message");
            status = json.getInt("status");
            data = ConvertMessageFromJson(json.getString("data"));
        } catch (Exception e) {
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
            status = -1;
            data = null;
        }

        WebServiceResult<Message> wsResults = new WebServiceResult<>();
        wsResults.status = status;
        wsResults.message = message;
        wsResults.data = data;

        listener.onFinish(wsResults);
    }

    private Message ConvertMessageFromJson(String data) throws JSONException
    {
        JSONObject jsonMessageObject = new JSONObject(data);
        Message message = new Message(
                jsonMessageObject.getString("text"),
                jsonMessageObject.getString("sender"),
                jsonMessageObject.getString("timeSend"),
                jsonMessageObject.getString("location"),
                jsonMessageObject.getString("type")
        );

        return message;
    }
}
