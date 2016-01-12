package hr.foi.air.t18.chatup;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Danijel on 12.1.2016..
 */
public class ConnectToService implements ServiceConnection {

    BackgroundService mService;
    boolean mBound = false;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        BackgroundService.LocalBinder mBinder = (BackgroundService.LocalBinder) service;
        mService = mBinder.getService();
        mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mBound = false;
    }

    public boolean isBound()
    {
        return this.mBound;
    }

    public BackgroundService connectedService()
    {
        return this.mService;
    }
}