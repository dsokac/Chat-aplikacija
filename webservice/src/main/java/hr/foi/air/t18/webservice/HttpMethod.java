package hr.foi.air.t18.webservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by Danijel on 24.10.2015..
 */
public abstract class HttpMethod
{
    HttpURLConnection connection;
    String url;
    StringBuffer response;

    public HttpMethod(String url)
    {
        this.url = url;
        this.response = new StringBuffer();
    }

    public String getURL()
    {
        return this.url;
    }

    public String getResponse()
    {
        return response.toString();
    }

    public abstract void sendRequest() throws IOException;
    public abstract void sendRequest(Map<String, String> data) throws IOException;
}
