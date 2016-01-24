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

import java.util.Objects;


/**
 * Class which connects to socket server and establish connection with it.
 * It also registers client to socket server.
 *
 * Created by Danijel on 17.1.2016..
 */
public class CreateSocketConnectionAsync extends SocketAbstractAsync implements ISocketOperation {

    private Context ctx;
    private Socket socket;

    public CreateSocketConnectionAsync(SocketNotificationsManager socketNotificationsManager, JSONObject params)
    {
        super(socketNotificationsManager,params);
        this.ctx = socketNotificationsManager.getContext();
        this.socket = socketNotificationsManager.getSocket();
    }

    @Override
    public void sendToServer(SocketEvents eventName, JSONObject params) {
        try {
            this.socket.connect();

            super.sendToServer(SocketEvents.registration,
                    this.parseRegistrationData(
                            "id",
                            this.getIDFromSharedPreferences("UserEmail", ctx)));
        } catch (Exception ex)
        {
            Log.e("error", "sendToServer: ",ex );
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Toast.makeText(ctx,"Connected to Socket",Toast.LENGTH_SHORT).show();
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

    /*@Override
    public void sendToServer(SocketEvents eventName, JSONObject params) {

    }*/
}
