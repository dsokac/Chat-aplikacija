package hr.foi.air.t18.chatup.SocketNotifications;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import hr.foi.air.t18.chatup.SocketNotifications.BackgroundService;

/**
 * Created by Danijel on 12.1.2016..
 */
public class ConnectToService implements ServiceConnection {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        /*BackgroundService.LocalBinder mBinder = (BackgroundService.LocalBinder) service;
        mService = mBinder.getService();*/

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

}