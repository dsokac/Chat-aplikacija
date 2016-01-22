package hr.foi.air.t18.webservice.MainAsync;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.HttpPOST;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;
import hr.foi.air.t18.webservice.WebServiceStrings;

/**
 * AsyncTask class that fetches required user data from the Web service.
 *
 * Created by Danijel on 22.12.2015..
 */
public class FetchUserDataAsync extends AsyncTask<Void, Void, String>
{
    private IListener<User> listener;
    private String email;

    /**
     * Constructor of FetchUserDataAsync class.
     * @param email E-mail address of the user
     * @param listener IListener object that implements onBegin() and onFinish() methods
     */
    public FetchUserDataAsync(String email, IListener<User> listener)
    {
        this.email = email;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", email);

        try
        {
            HttpPOST connection = new HttpPOST(WebServiceStrings.SERVER + WebServiceStrings.GET_USER_DATA);
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
        String message;
        int status;
        User data;

        try
        {
            JSONObject jsonObject = new JSONObject(result);
            message = jsonObject.getString("message");
            status = jsonObject.getInt("status");
            data = ConvertUserFromJson(jsonObject.getString("data"));
        }
        catch (Exception e)
        {
            message = "Error parsing JSON. Either JSON is invalid or server is down.";
            status = -1;
            data = null;
        }

        WebServiceResult<User> wsResult = new WebServiceResult<>();
        wsResult.status = status;
        wsResult.message = message;
        wsResult.data = data;
        listener.onFinish(wsResult);
    }

    private User ConvertUserFromJson(String data) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(data);
        User user = new User();

        user.setEmail(jsonObject.getString("email"));
        user.setUsername(jsonObject.getString("username"));
        return user;
    }
}
