package hr.foi.air.t18.webservice;

/**
 *
 * Interface for a Listener that will have implemented onBegin()
 * and onFinish() methods. Mainly used to show the loading animation
 * and to access the objects it generates when it finishes.
 *
 * Created by Danijel on 15.11.2015..
 */
public interface IListener<T>
{
    /**
     * What happens just as async task begins.
     */
    void onBegin();

    /**
     * What happens when async task finishes.
     * @param result Data structure that holds the Web service result
     */
    void onFinish(WebServiceResult<T> result);
}
