package hr.foi.air.t18.chatup.SocketNotifications;

import android.os.AsyncTask;

import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Danijel on 17.1.2016..
 */
public class BackgroundProcess extends AsyncTask<Void, Void, String> {

    private Context ctx;
    public AppSocket socket;
    private String[] result;
    IListener<String> listener;
    private BackgroundProcess bp;


    public BackgroundProcess(IListener<String> listener)
    {
        this.listener = listener;
        this.result = new String[1];
        result[0] = null;
        this.bp = this;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
       // this.socket = new AppSocket(ctx, bp);
        this.socket.ConnectToServer();
       // this.socket.RegisterClient("UserEmail", this.ctx);
    }

    @Override
    protected String doInBackground(Void... params) {

        return result[0];
    }

    @Override
    protected void onPostExecute(String s) {

        WebServiceResult<String> wsR = new WebServiceResult<>();
        wsR.message = s;
        this.listener.onFinish(wsR);
    }

    @Override
    protected void onCancelled() {
        this.socket.DisconnectFromServer();
        this.socket = null;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public void setResult(String[] result) {
        this.result = result;
    }

}
