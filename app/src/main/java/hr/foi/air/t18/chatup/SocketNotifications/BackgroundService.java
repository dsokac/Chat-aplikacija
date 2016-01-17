package hr.foi.air.t18.chatup.SocketNotifications;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Danijel on 12.1.2016..
 */
public class BackgroundService extends Service {
    private int mStartMode;

   // private final IBinder mBinder = new LocalBinder();

    private IBinder mBinder = null;

    private BackgroundProcess backProcess;

    private Activity activity;

    private AppSocket socket;

    /*public class LocalBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*this.backProcess = new BackgroundProcess(new IListener<String>() {
            @Override
            public void onBegin() {

            }

            @Override
            public void onFinish(WebServiceResult<String> result) {
                Toast.makeText(getApplicationContext(), result.message.toString(), Toast.LENGTH_LONG).show();
            }
        })*/;
       // backProcess.setCtx(getApplicationContext());

        /*mHandler = new Handler(Looper.myLooper())
        {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getApplicationContext(),msg.toString(),Toast.LENGTH_LONG).show();
            }
        };*/

        //backProcess.execute();

        this.mStartMode = START_STICKY;
        return mStartMode;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "Bound...", Toast.LENGTH_LONG).show();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        //this.backProcess.socket.DisconnectFromServer();
        super.onDestroy();
    }

    /*public AppSocket getSocket() {
        return this.backProcess.socket;
    }*/


}