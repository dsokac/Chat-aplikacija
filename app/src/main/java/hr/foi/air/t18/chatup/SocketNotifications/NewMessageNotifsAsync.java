package hr.foi.air.t18.chatup.SocketNotifications;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

/**
 * Created by Laptop on 19.1.2016..
 */
public class NewMessageNotifsAsync extends SocketAbstractAsync implements ISocketOperation{

    public NewMessageNotifsAsync(SocketNotificationsManager socketNotificationsManager,JSONObject params)
    {
        super(socketNotificationsManager,params);
    }

    @Override
    public void sendToServer(SocketEvents eventName, JSONObject params) {
        super.sendToServer(SocketEvents.newMessage, params);
    }

    public NewMessageNotifsAsync(){super();}

    @Override
    public void listenServer(SocketNotificationsManager snManager, SocketEvents socketEvents, String title, int icon, int notifID) {
        super.listenServer(snManager, socketEvents, title, icon, notifID);
    }

    /*private Socket socket;
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
    }*/

    /***
     * Function sends new message notification to socket server.
     * @param eventName - event name for new message is newMessage
     * @param params - params that should be sent to server
     */
/*
    public void sendToServer(String eventName, JSONObject params) {
        this.socket.emit(eventName,params);
    }

    @Override
    public void sendToServer(SocketEvents eventName, JSONObject params) {

    }*/
}
