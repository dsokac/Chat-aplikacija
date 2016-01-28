package hr.foi.air.t18.socketnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;

import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Created by Danijel on 23.1.2016..
 */

public abstract class SocketAbstractAsync extends AsyncTask<Object,Void,Object> implements ISocketOperation{
    private SocketNotificationsManager socketNotificationsManager;
    private SocketEvents socketEvents;
    private JSONObject params;

    public SocketAbstractAsync(SocketNotificationsManager socketNotificationsManager, JSONObject params)
    {
        this.socketNotificationsManager = socketNotificationsManager;
        this.params = params;
    }

    @Override
    public void sendToServer(SocketEvents eventName, JSONObject params) {
         this.socketEvents = eventName;
         this.socketNotificationsManager.getSocket().emit(eventName.getEvent(), params);
    }

    @Override
    public Object doInBackground(Object... params) {
         this.sendToServer(this.socketEvents, this.params);
         return null;
    }

    public SocketAbstractAsync(){}

    @Override
    public void listenServer(final SocketNotificationsManager snManager, SocketEvents socketEvents,final String title,final int icon)
    {
        snManager.getSocket().on(socketEvents.getEvent(), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showNotification(snManager, title, args[0].toString(),icon);
                        return;
                    }
                });

            }
        });
    }

    private void showNotification(SocketNotificationsManager snManager, String title, String content, int icon)
    {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(snManager.getContext())
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(content);


        int mNotificationId = (int)System.currentTimeMillis();
        NotificationManager mNotifyMgr =
                (NotificationManager) snManager.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }
}
