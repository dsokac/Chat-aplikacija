package hr.foi.air.t18.socketnotifications;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Class which implements Service connection functions to handle binding to a service
 *
 * Created by Danijel on 12.1.2016..
 */
public class ConnectToService implements ServiceConnection {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

}