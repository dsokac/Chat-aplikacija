package hr.foi.air.t18.chatup.Notifications;

import android.app.PendingIntent;

import org.json.JSONObject;

import hr.foi.air.t18.socketnotifications.SocketAbstractAsync;
import hr.foi.air.t18.socketnotifications.SocketEvents;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;

/**
 * Class used to send friend requests to socket server.
 * Created by Danijel on 17.1.2016..
 */
public class FriendRequestNotifsAsync extends SocketAbstractAsync {

    public FriendRequestNotifsAsync(SocketNotificationsManager socketNotificationsManager, JSONObject params) {
        super(socketNotificationsManager, params);
    }

    /***
     * Defines event which occures when you add friend
     * @param socketEvents
     * @param params
     */
    @Override
    public void sendToServer(SocketEvents socketEvents, JSONObject params) {
        super.sendToServer(SocketEvents.friendRequest, params);
    }


    public FriendRequestNotifsAsync(){super();}

    /***
     * Defines listener which wait untill somebody adds you. And server should notify you then.
     * @param snManager - socket notification manager, which manages whole module
     * @param socketEvents
     * @param title
     * @param icon
     * @param pendingIntent
     */
    @Override
    public void listenServer(SocketNotificationsManager snManager, SocketEvents socketEvents, String title, int icon, PendingIntent pendingIntent) {
        super.listenServer(snManager, socketEvents, title, icon, pendingIntent);
    }
}
