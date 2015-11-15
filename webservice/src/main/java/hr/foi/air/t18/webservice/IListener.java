package hr.foi.air.t18.webservice;

/**
 *
 * Interface for a Listener that will have implemented onBegin()
 * and onFinish() methods. Mainly used to show the loading animation
 * and to access the objects it generates when it finishes.
 *
 * Created by Danijel on 15.11.2015..
 */
public interface IListener
{
    /**
     * What happens just as async task begins.
     */
    public void onBegin();

    /**
     * What happens when async task finishes.
     * @param status Status cose of response (custom)
     * @param message Message retrieved with response (could also be data)
     */
    public void onFinish(int status, String message);
}
