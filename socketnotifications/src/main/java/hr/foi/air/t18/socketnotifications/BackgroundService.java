package hr.foi.air.t18.socketnotifications;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Class for handling background service.
 * It runs background service on application's start up.
 *
 * Created by Danijel on 12.1.2016..
 */
public class BackgroundService extends Service {

    //start mode of running service
    private int mStartMode;

    private IBinder mBinder = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mStartMode = START_STICKY;
        return mStartMode;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
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
        super.onDestroy();
    }

    public void executeAsyncTask(AsyncTask<Object,Void,Object> asyncTask, Object... params)
    {
          asyncTask.execute(params);
    }

}