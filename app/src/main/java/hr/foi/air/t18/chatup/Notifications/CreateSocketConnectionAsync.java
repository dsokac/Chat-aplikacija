package hr.foi.air.t18.chatup.Notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import hr.foi.air.t18.socketnotifications.ISocketOperation;
import hr.foi.air.t18.socketnotifications.SocketAbstractAsync;
import hr.foi.air.t18.socketnotifications.SocketEvents;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;


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

    /***
     * Defines how android sends message to server.
     * @param eventName - defined name for socket event
     * @param params - all params that should be sent to server
     */
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
