package hr.foi.air.t18.socketnotifications;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


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

    private Socket socket;

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
        try {
            this.socket = IO.socket(socketAddr);
        }catch (Exception ex)
        {
            Log.e("error", "SocketNotificationsManager: ", ex );
        }

    }

    /***
     * Function to start background service
     */
    private void runBackgroundService()
    {
        this.backgroundService = new BackgroundService();
        this.context.startService(new Intent(this.context,BackgroundService.class));
        this.isServiceRunning = true;
        this.bindToService();
    }

    /***
     * Function to stop background service
     */
    public void stopBackgroundService()
    {
        if(this.isBound)
        {
            this.context.unbindService(this.connection);
            this.socket.disconnect();
            this.socket = null;
            this.isBound = false;
        }
        this.context.stopService(new Intent(this.context,BackgroundService.class));
    }


    /***
     * Function which binds application context to service.
     */
    public void bindToService()
    {
        if(this.isServiceRunning)
        {
            this.context.bindService(new Intent(this.context, BackgroundService.class), this.connection, Context.BIND_AUTO_CREATE);
            this.isBound = true;
        }
    }

    /***
     * Function to run async tasks which should send request to socket server
     * @param asyncTask - async task you want to execute, anonymous object
     * @param params - params for async tasks
     */
    public void attachAsyncTasks(AsyncTask<Object,Void,Object> asyncTask, Object... params)
    {
         backgroundService.executeAsyncTask(asyncTask,params);
    }

    /***
     * Function to store updated socket
     * @param socket - socket which updated
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /***
     * Function to get stored socket server address
     * @return socket server address
     */
    protected String getSocketAddr() {
        return socketAddr;
    }

    /***
     * Function to get stored application context
     * @return application context
     */
    public Context getContext() {
        return context;
    }

    /***
     * Function to get stored socket
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }
}
