package hr.foi.air.t18.chatup;

import android.content.Context;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import hr.foi.air.t18.core.SharedPreferencesClass;

/**
 * Created by Danijel on 12.1.2016..
 */
public class AppSocket{

    private String remoteIP = "104.236.58.50";
    private String localIP = "192.168.1.2";
    private Socket socket;

    public AppSocket()
    {
        try
        {
            this.socket = IO.socket("http://" + this.localIP + ":3000/");
        }catch (Exception ex)
        {
            Log.e("error", "AppSocket: ", ex );
        }
        GetSocket();
    }

    public Socket GetSocket()
    {
        return this.socket;
    }

    public void RegisterClient(String sharedPrefKey, Context ctx)
    {
        String id = SharedPreferencesClass.getDefaults(sharedPrefKey, ctx);
        this.socket.emit("registration",this.parseRegistrationData(id));
    }

    public void ConnectToServer()
    {
        this.socket.connect();
    }

    public void DisconnectFromServer()
    {
        this.socket.disconnect();
    }

    private JSONObject parseRegistrationData(String id)
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("id",id);
        }catch(Exception ex)
        {
            Log.e("error", "parseRegistrationData: ", ex );
        }

        return json;
    }

    public void NotifyFriendRequest(String from, String to)
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("from",from);
            json.put("to",to);
        }catch(Exception ex)
        {
            Log.e("error", "NotifyFriendRequest: ", ex);
        }

        this.socket.emit("friendRequest",json);
    }

    public void NotifyNewMessage(String from, String to)
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("from",from);
            json.put("to",to);
        }catch(Exception ex)
        {
            Log.e("error", "NotifyNewMessage: ", ex);
        }

        this.socket.emit("newMessage",json);
    }
}

