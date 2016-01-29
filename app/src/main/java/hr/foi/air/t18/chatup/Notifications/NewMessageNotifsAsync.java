package hr.foi.air.t18.chatup.Notifications;

import android.app.PendingIntent;

import org.json.JSONObject;

import hr.foi.air.t18.socketnotifications.ISocketOperation;
import hr.foi.air.t18.socketnotifications.SocketAbstractAsync;
import hr.foi.air.t18.socketnotifications.SocketEvents;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;

/**
 * Created by Laptop on 19.1.2016..
 */
public class NewMessageNotifsAsync extends SocketAbstractAsync implements ISocketOperation {

    public NewMessageNotifsAsync(SocketNotificationsManager socketNotificationsManager,JSONObject params)
    {
        super(socketNotificationsManager,params);
    }

    /***
     * Defines function which sends notification to server when you send a message.
     * @param eventName - name of socket event
     * @param params - parameters
     */
    @Override
    public void sendToServer(SocketEvents eventName, JSONObject params) {
        super.sendToServer(SocketEvents.newMessage, params);
    }

    public NewMessageNotifsAsync(){super();}


    /***
     * Function which listen when server sends you notification
     * that somebody has sent you a message
     * @param snManager - socket notification manager which manages whole notification module
     * @param socketEvents - socket event
     * @param title - title of notification
     * @param icon - icon for notification
     * @param pendingIntent
     */
    @Override
    public void listenServer(SocketNotificationsManager snManager, SocketEvents socketEvents, String title, int icon, PendingIntent pendingIntent) {
        super.listenServer(snManager, socketEvents, title, icon, pendingIntent);

    }

}
