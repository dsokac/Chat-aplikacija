package hr.foi.air.t18.chatup;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

/**
 * Created by Danijel on 12.1.2016..
 */
public class BackgroundService extends Service {
    private int mStartMode;

    private Thread t;

    private final IBinder mBinder = new LocalBinder();

    private boolean mAllowedRebind;

    private AppSocket socket;

    public class LocalBinder extends Binder {
        public BackgroundService getService()
        {
            return BackgroundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ConnectToSocketServer();

        socket.GetSocket().on("friendRequest", new Emitter.Listener() {

            @Override
            public void call(final Object... args) {
              /*  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),args[0].toString(),Toast.LENGTH_LONG).show();
                        return;
                    }
                }).start();*/
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                socket.RegisterClient("UserEmail",getApplicationContext());
            }
        }).start();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "Bound...", Toast.LENGTH_LONG).show();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return this.mAllowedRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.socket.DisconnectFromServer();
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show();
    }

    private void task()
    {

    }

    public AppSocket useSocket()
    {
        return this.socket;
    }

  /*  public void NotifyFriendRequest(final String from, final String to)
    {
        final String tFrom = from;
        final String tTo = to;

        new Thread(new Runnable() {
            @Override
            public void run() {
                socket.NotifyFriendRequest(tFrom,tTo);
            }
        }).start();
    }*/

    public void ConnectToSocketServer()
    {
        try
        {
            this.socket = new AppSocket();
            this.socket.ConnectToServer();
        }
        catch (Exception ex)
        {
            Log.e("error", "onStartCommand: ", ex);
        }
    }

}