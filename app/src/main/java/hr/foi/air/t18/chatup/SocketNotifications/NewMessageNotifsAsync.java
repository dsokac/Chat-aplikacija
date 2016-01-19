package hr.foi.air.t18.chatup.SocketNotifications;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

/**
 * Created by Laptop on 19.1.2016..
 */
public class NewMessageNotifsAsync extends AsyncTask<Object,Void,Object> implements ISocketOperation{

    private Socket socket;
    private SocketNotificationsManager socketNotificationsManager;

    public NewMessageNotifsAsync(SocketNotificationsManager socketNotificationsManager) {
        this.socketNotificationsManager = socketNotificationsManager;
    }

    @Override
    protected void onPreExecute() {
        this.socket = this.socketNotificationsManager.getSocket();
    }

    @Override
    protected Object doInBackground(Object... params) {
        this.sendToServer("newMessage", (JSONObject)params[0]);
        return null;
    }

    /***
     * Function sends new message notification to socket server.
     * @param eventName - event name for new message is newMessage
     * @param params - params that should be sent to server
     */
    @Override
    public void sendToServer(String eventName, JSONObject params) {
        this.socket.emit(eventName,params);
    }
}
