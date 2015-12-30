package hr.foi.air.t18.webservice;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.Message;
import hr.foi.air.t18.core.User;

/**
 * An AsyncTask class that fetches all the conversation the user
 * participates in.
 *
 * Created by Danijel on 29.11.2015..
 */
public class FetchMessagesAsync extends AsyncTask<Void, Void, String>
{
    private User user;
    private IListener<ArrayList<Conversation>> listener;

    /**
     * Constructor for FetchMessagesAsync class
     * @param user User object
     * @param listener IListener object that implement onBegin() and onFinish() methods
     */
    public FetchMessagesAsync(User user, IListener<ArrayList<Conversation>> listener)
    {
        this.user = user;
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
        parameters.put("email", user.getEmail());

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
        ArrayList<Conversation> data;

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

        WebServiceResult<ArrayList<Conversation>> wsResults = new WebServiceResult<>();
        wsResults.status = status;
        wsResults.message = message;
        wsResults.data = data;

        listener.onFinish(wsResults);
    }

    private ArrayList<Conversation> ConvertConversationsFromJSON(String data) throws JSONException
    {
        ArrayList<Conversation> conversations= new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            Conversation conversation = new Conversation();
            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

            conversation.setID(jsonObject.getString("id"));
            conversation = addParticipantsToConversation(jsonObject.getString("participants"), conversation);
            conversation = addMessagesToConversation(jsonObject.getString("chat"), conversation);

            conversations.add(conversation);
        }

        return conversations;
    }

    private Conversation addParticipantsToConversation(String participants, Conversation conversation) throws JSONException
    {
        JSONArray jsonArray = new JSONArray(participants);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

            User user = new User();
            user.setEmail(jsonObject.getString("email"));
            user.setUsername(jsonObject.getString("username"));

            conversation.addParticipant(user);
        }

        return conversation;
    }

    private Conversation addMessagesToConversation(String chat, Conversation conversation) throws JSONException
    {
        JSONArray jsonArray = new JSONArray(chat);

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

            conversation.addMessage(message);
        }

        return conversation;
    }
}
