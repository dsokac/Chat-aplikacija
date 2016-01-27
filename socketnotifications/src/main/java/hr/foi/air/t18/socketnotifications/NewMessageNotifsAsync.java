package hr.foi.air.t18.socketnotifications;

import android.app.PendingIntent;

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
    public void listenServer(SocketNotificationsManager snManager, SocketEvents socketEvents, String title, int icon) {
        super.listenServer(snManager, socketEvents, title, icon);

    }

}
