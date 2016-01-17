package hr.foi.air.t18.chatup.SocketNotifications;

import org.json.JSONObject;

/**
 * Interface with function necessary for sending request to socket server
 * Created by Danijel on 17.1.2016..
 */
public interface ISocketOperation {
      public void sendToServer(String eventName, JSONObject params);
}
