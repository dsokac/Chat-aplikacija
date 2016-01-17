package hr.foi.air.t18.chatup.SocketNotifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;


/**
 * Class which connects to socket server and establish connection with it.
 * It also registers client to socket server.
 *
 * Created by Danijel on 17.1.2016..
 */
public class CreateSocketConnectionAsync extends AsyncTask<Object,Void,Object> implements ISocketOperation {

    private Socket socket;

    public SocketNotificationsManager socketNotificationsManager;

    public CreateSocketConnectionAsync(SocketNotificationsManager socketNotificationsManager)
    {
        this.socketNotificationsManager = socketNotificationsManager;
    }

    @Override
    protected void onPreExecute() {
        try
        {
            //establish connection with socket server
            this.socket = IO.socket(socketNotificationsManager.getSocketAddr());

            //connect
            this.socket.connect();
            this.sendToServer(
                    "registration",
                    this.parseRegistrationData(
                            "id",
                            this.getIDFromSharedPreferences("UserEmail", socketNotificationsManager.getContext()
                            )
                    ));
            this.socketNotificationsManager.setSocket(this.socket);

        }catch(Exception ex)
        {
            Log.e("error", "onPreExecute: ", ex );
        }
    }

    @Override
    protected Object doInBackground(Object... params) {
        return "";
    }

    @Override
    protected void onPostExecute(Object s) {
        Toast.makeText(socketNotificationsManager.getContext(),"Connected to Socket",Toast.LENGTH_SHORT).show();
    }

    /***
     * Function from interface which sends request to server for registration.
     * @param eventName - event name for registration is "registration"
     * @param params - send registration params
     */
    @Override
    public void sendToServer(String eventName, JSONObject params) {
        this.socket.emit(eventName,params);
    }


    /**
     * Function which parse data neccessary for registration.
     * @param key - key for socket server to
     * @param value - value from shared preferences
     * @return - json object which is sent to socket server
     */
    private JSONObject parseRegistrationData(String key, String value)
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put(key,value);
        }catch(Exception ex)
        {
            Log.e("error", "parseRegistrationData: ", ex );
        }

        return json;
    }

    /***
     * Function gets value from shared preferences based on key
     * @param sharedPrefKey - key of shared preference we want to fetch
     * @param ctx - context of application
     * @return - String value of shared preference based on sent key
     */
    private String getIDFromSharedPreferences(String sharedPrefKey, Context ctx)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getString(sharedPrefKey,"");
    }

}
