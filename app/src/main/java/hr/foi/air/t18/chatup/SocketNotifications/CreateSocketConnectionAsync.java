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

import hr.foi.air.t18.core.SharedPreferencesClass;

/**
 * Created by Danijel on 17.1.2016..
 */
public class CreateSocketConnectionAsync extends AsyncTask<Void,Void,String> implements ISocketOperation {

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
            this.socket = IO.socket(socketNotificationsManager.getSocketAddr());
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
    protected String doInBackground(Void... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(socketNotificationsManager.getContext(),"Connected to Socket",Toast.LENGTH_SHORT).show();
    }

    @Override
    public Socket listenServer(Socket socket) {
        return null;
    }

    @Override
    public void sendToServer(String eventName, JSONObject params) {
        this.socket.emit(eventName,params);
    }

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

    private String getIDFromSharedPreferences(String sharedPrefKey, Context ctx)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getString(sharedPrefKey,"");
    }

}
