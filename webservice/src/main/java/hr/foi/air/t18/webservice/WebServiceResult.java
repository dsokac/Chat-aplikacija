package hr.foi.air.t18.webservice;

/**
 * Created by Danijel on 29.11.2015..
 */
public class WebServiceResult<T>
{
    public int status;
    public String message;
    public T data;
}