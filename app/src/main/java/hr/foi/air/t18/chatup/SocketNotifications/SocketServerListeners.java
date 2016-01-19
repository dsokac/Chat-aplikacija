package hr.foi.air.t18.chatup.SocketNotifications;


import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Class to handle all listeners for socket server's push notifications.
 * Created by Danijel on 17.1.2016..
 */
public class SocketServerListeners{

    //Socket notification manager connects its properties to this class.
    private SocketNotificationsManager socketNotificationsManager;

    //Socket to be stored after creation
    private Socket socket;

    /***
     * Initializes all private properties of this class. And here should be all listeners added.
     * FriendRequestListener is listener for friend requests.
     * newMessageRequestListener is listener for new message.
     * @param socketNotificationsManager - connects its properties to this class (socket, context,...)
     */
    public SocketServerListeners(SocketNotificationsManager socketNotificationsManager)
    {
        this.socketNotificationsManager = socketNotificationsManager;
        this.socket = this.socketNotificationsManager.getSocket();

        // new listeners go below existing ones
        this.FriendRequestListener();
    }

    /***
     * Function which updates socket.
     * @return - returns updated socket
     */
    public Socket refreshSocket()
    {
        return this.socket;
    }

    /***
     * Function attaches event listener to socket which waits for new message.
     */
    private void newMessageRequestListener() {
        this.socket.on("newMessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(socketNotificationsManager.getContext(), args[0].toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                });
            }
        });
    }

    /***
     * Function attaches event listener to socket which waits for friend requests.
     */
    private void FriendRequestListener()
    {
        this.socket.on("friendRequest", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(socketNotificationsManager.getContext(), args[0].toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                });

            }
        });
    }






}