package hr.foi.air.t18.webservice.ConversationAsync;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.Pair;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Danijel on 10.1.2016..
 */
public class AddParticipantsToConversationAsyncTask extends AsyncTask<Void, Void, String>
{
    private String conversationID;
    private ArrayList<String> emailBuffer;
    private IListener<ArrayList<String>> listener;

    public AddParticipantsToConversationAsyncTask
    (String conversationID, ArrayList<String> emailBuffer, IListener<ArrayList<String>> listener)
    {
        this.conversationID = conversationID;
        this.emailBuffer = emailBuffer;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        ArrayList<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("id", conversationID));
        for (int i = 0; i < emailBuffer.size(); i++)
            parameters.add(new Pair<>("emails[]", emailBuffer.get(i)));

        try
        {
            HttpPOST connection =
                    new HttpPOST("http://104.236.58.50:8080/addParticipantsToConversation");

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
        ArrayList<String> data;

        try
        {
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");
            data = jsonParseData(result);
        }
        catch (Exception e)
        {
            status = -1;
            message = e.getMessage();
            data = null;
        }

        WebServiceResult<ArrayList<String>> wsResult =
                new WebServiceResult<>();

        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = data;
        listener.onFinish(wsResult);
    }

    private ArrayList<String> jsonParseData(String json)
    {
        ArrayList<String> data = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++)
                data.add(jsonArray.getString(i));
        }
        catch (Exception e)
        {
            data = null;
        }
        return data;
    }
}
