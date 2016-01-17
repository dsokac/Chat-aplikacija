package hr.foi.air.t18.chatup.SocketNotifications;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

/**
 * Class used to send friend requests to socket server.
 * Created by Danijel on 17.1.2016..
 */
public class FriendRequestNotifsAsync extends AsyncTask<Object,Void,Object> implements ISocketOperation {


    private Socket socket;
    private SocketNotificationsManager socketNotificationsManager;

    public FriendRequestNotifsAsync(SocketNotificationsManager socketNotificationsManager)
    {
        this.socketNotificationsManager = socketNotificationsManager;
    }

    @Override
    protected void onPreExecute() {
        this.socket = this.socketNotificationsManager.getSocket();
    }

    @Override
    protected Object doInBackground(Object... params) {
        this.sendToServer("friendRequest", (JSONObject)params[0]);
        return null;
    }

    /***
     * Function sends friend request to socket server.
     * @param eventName - event name for friend requests is friendRequest
     * @param params - params that should be sent to server
     */
    @Override
    public void sendToServer(String eventName, JSONObject params) {
         this.socket.emit(eventName,params);
    }
}
