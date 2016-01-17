package hr.foi.air.t18.chatup.SocketNotifications;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.Objects;

/**
 * Manager to manage notifications using socket.io package.
 *
 * Created by Danijel on 17.1.2016..
 */
public class SocketNotificationsManager {

    //Local property to save socket address
    private String socketAddr;

    //Context where this class is running
    private Context context;

    //Background service that will be run
    private BackgroundService backgroundService;

    //local variable to check if service is running
    private boolean isServiceRunning = false;

    //variable to check if someone is bound to this service
    private boolean isBound = false;

    private ConnectToService connection = new ConnectToService();

    /***
     * Constructor to initialize notifications using socket.
     * @param socketAddr - socket server address, type: String
     * @param context - application context, type: Context
     */
    public SocketNotificationsManager(String socketAddr, Context context)
    {
        this.socketAddr = socketAddr;
        this.context = context;
        this.runBackgroundService();
    }

    /***
     * Function to start background service
     */
    private void runBackgroundService()
    {
        this.backgroundService = new BackgroundService();
        this.context.startService(new Intent(this.context,BackgroundService.class));
        this.isServiceRunning = true;
    }

    /***
     * Function to stop background service
     */
    public void stopBackgroundService()
    {
        if(this.isBound)
        {
            this.context.unbindService(this.connection);
            this.isBound = false;
        }
        this.context.stopService(new Intent(this.context,BackgroundService.class));
    }


    /***
     * -function which binds application context to service.
     */
    public void bindToService()
    {
        if(this.isServiceRunning)
        {
            this.context.bindService(new Intent(this.context, BackgroundService.class), this.connection, Context.BIND_AUTO_CREATE);
            this.isBound = true;
        }
    }

    public void attachAsyncTasks(AsyncTask asyncTask)
    {
         asyncTask.execute();
    }



}
