package hr.foi.air.t18.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class used for sending POST requests to Web services.
 * Created by Danijel on 24.10.2015..
 */
public class HttpPOST extends HttpMethod
{
    private String query = "";

    /**
     * Constructor.
     * @param url Web service URL as String.
     */
    public HttpPOST(String url)
    {
        super(url);
    }

    /**
     * Sends empty POST request to Web service.
     * @throws IOException
     */
    @Override
    public void sendRequest() throws IOException
    {
        try {
            URL url = new URL(this.url);
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestMethod("POST");
            this.connection.setDoInput(true);

            int responseCode = this.connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
                String line;
                while ((line = input.readLine()) != null) {
                    this.response.append(line);
                }
            }
        } finally {
            this.connection.disconnect();
        }
    }

    /**
     * Sends POST request with parameters.
     * @param data POST parameters as Map object
     * @throws IOException
     */
    @Override
    public void sendRequest(Map<String, String> data) throws IOException
    {
        generateQuery(data);
        mSendRequest();
    }

    /**
     * Sends POST request with parameters.
     * @param data POST parameters as list of Pair objects
     * @throws IOException
     */
    @Override
    public void sendRequest(ArrayList<Pair<String, String>> data) throws IOException
    {
        generateQuery(data);
        mSendRequest();
    }

    private void mSendRequest() throws IOException
    {
        byte[] dataBuffer = this.query.getBytes("UTF-8");
        try {
            URL url = new URL(this.url);
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestMethod("POST");
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.getOutputStream().write(dataBuffer);

            int responseCode = this.connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = input.readLine()) != null) {
                    this.response.append(line);
                }
            }
        } finally {
            this.connection.disconnect();
        }
    }

    /**
     * Morphs Map query into String query.
     * @param data Map query
     */
    private void generateQuery(Map<String, String> data)
    {
        String query = "";
        for (String key : data.keySet()) {
            query += key + "=" + data.get(key) + "&";
        }
        this.query = query.substring(0, query.lastIndexOf("&"));
    }

    /**
     * Morphs list of Pair queries into String query.
     * @param data List of Pair queries
     */
    private void generateQuery(ArrayList<Pair<String, String>> data)
    {
        String query = "";
        for (Pair<String, String> param : data)
        {
            query += param.getFirstValue() + "=" + param.getSecondValue() + "&";
        }
        this.query = query.substring(0, query.lastIndexOf("&"));
    }
}
