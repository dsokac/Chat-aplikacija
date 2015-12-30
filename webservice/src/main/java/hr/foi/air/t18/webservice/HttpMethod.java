package hr.foi.air.t18.webservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Abstract class that defines the basic behavior of it's children
 * for contacting Web services.
 * Created by Danijel on 24.10.2015..
 */
public abstract class HttpMethod
{
    HttpURLConnection connection;
    String url;
    StringBuffer response;

    /**
     * Default constructor.
     * @param url Web service URL as String.
     */
    public HttpMethod(String url)
    {
        this.url = url;
        this.response = new StringBuffer();
    }

    /**
     * Gets Web service URL.
     * @return URL as String.
     */
    public String getURL()
    {
        return this.url;
    }

    /**
     * Gets Web service response as String.
     * @return Web service response as String
     */
    public String getResponse()
    {
        return response.toString();
    }

    /**
     * Sends a request to Web service without any parameters.
     * @throws IOException
     */
    public abstract void sendRequest() throws IOException;

    /**
     * Sends a request to Web service with POST or GET parameters (defined in children).
     * @param data POST or GET parameters as Map object
     * @throws IOException
     */
    public abstract void sendRequest(Map<String, String> data) throws IOException;
}
