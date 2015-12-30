package hr.foi.air.t18.webservice;

/**
 * Class that acts as a structure for data sent by a
 * Web service.
 */
public class WebServiceResult<T>
{
    public int status;
    public String message;
    public T data;
}
