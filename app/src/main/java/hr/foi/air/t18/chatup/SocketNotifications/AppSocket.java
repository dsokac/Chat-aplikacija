package hr.foi.air.t18.chatup.SocketNotifications;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import hr.foi.air.t18.core.SharedPreferencesClass;

import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Created by Danijel on 12.1.2016..
 */
public class AppSocket{

    private String remoteIP = "104.236.58.50";
    private String localIP = "192.168.1.39";
    private Socket socket;
    private Context ctx;
    private BackgroundProcess bp;

    public AppSocket(Context ctx, BackgroundProcess bp)
    {
        try
        {
            this.socket = IO.socket("http://" + this.localIP + ":3000/");
            this.ctx = ctx;
            this.bp = bp;
        }catch (Exception ex)
        {
            Log.e("error", "AppSocket: ", ex );
        }
        GetSocket();
    }

    public Socket GetSocket()
    {
        this.socket.on("friendRequest", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, args[0].toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                });

            }
        });
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


}

