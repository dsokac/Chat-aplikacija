package hr.foi.air.t18.webservice.ConversationAsync;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;
import hr.foi.air.t18.webservice.WebServiceStrings;

/**
 * An AsyncTask class that contacts the server and creates the conversation
 * between two users.
 *
 * Created by Danijel on 23.12.2015..
 */
public class CreateConversationAsync extends AsyncTask<Void, Void, String>
{
    private String email1;
    private String email2;
    private IListener<Void> listener;

    /**
     * Constructor for CreateConversationAsync class.
     * @param email1 E-mail address of one user
     * @param email2 E-mail address of the second user
     * @param listener IListener object that implements onBegin() and onFinish() methods
     */
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
            HttpPOST connection = new HttpPOST(WebServiceStrings.SERVER + WebServiceStrings.CREATE_CONVERSATION);
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
