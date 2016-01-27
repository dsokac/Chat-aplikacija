package hr.foi.air.t18.socketnotifications;

import org.json.JSONObject;

/**
 * Class used to send friend requests to socket server.
 * Created by Danijel on 17.1.2016..
 */
public class FriendRequestNotifsAsync extends SocketAbstractAsync{

    public FriendRequestNotifsAsync(SocketNotificationsManager socketNotificationsManager, JSONObject params) {
        super(socketNotificationsManager, params);
    }

    @Override
    public void sendToServer(SocketEvents socketEvents, JSONObject params) {
        super.sendToServer(SocketEvents.friendRequest, params);
    }


    public FriendRequestNotifsAsync(){super();}

    @Override
    public void listenServer(SocketNotificationsManager snManager, SocketEvents socketEvents, String title, int icon) {
        super.listenServer(snManager, socketEvents, title, icon);
    }
}