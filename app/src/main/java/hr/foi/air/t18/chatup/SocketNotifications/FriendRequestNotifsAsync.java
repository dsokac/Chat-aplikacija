package hr.foi.air.t18.chatup.SocketNotifications;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

/**
 * Created by Danijel on 17.1.2016..
 */
public class FriendRequestNotifsAsync extends AsyncTask<Void,Void,String> implements ISocketOperation {

    SocketNotificationsManager socketNotificationsManager;

    public FriendRequestNotifsAsync(SocketNotificationsManager socketNotificationsManager)
    {
        this.socketNotificationsManager = socketNotificationsManager;
    }

    @Override
    protected String doInBackground(Void... params) {
        return null;
    }

    @Override
    public Socket listenServer(Socket socket) {
        return null;
    }

    @Override
    public void sendToServer(String eventName, JSONObject params) {

    }
}
