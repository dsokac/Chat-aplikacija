package hr.foi.air.t18.socketnotifications;

import android.app.PendingIntent;

import org.json.JSONObject;

/**
 * Interface with function necessary for sending request to socket server
 * Created by Danijel on 17.1.2016..
 */
public interface ISocketOperation {
      void sendToServer(SocketEvents eventName, JSONObject params);
      void listenServer(SocketNotificationsManager snManager, SocketEvents socketEvents, String title, int icon, PendingIntent pendingIntent);
}
