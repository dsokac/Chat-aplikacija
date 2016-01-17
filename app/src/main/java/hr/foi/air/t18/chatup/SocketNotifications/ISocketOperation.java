package hr.foi.air.t18.chatup.SocketNotifications;

import android.content.Context;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

/**
 * Created by Danijel on 17.1.2016..
 */
public interface ISocketOperation {
      public Socket listenServer(Socket socket);
      public void sendToServer(String eventName, JSONObject params);
}
