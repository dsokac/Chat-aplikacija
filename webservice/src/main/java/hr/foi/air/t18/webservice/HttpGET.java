package hr.foi.air.t18.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Class used for sending GET requests to Web services.
 * Created by Danijel on 24.10.2015..
 */
public class HttpGET extends HttpMethod
{
    private String query = "";

    public HttpGET(String urlString)
    {
        super(urlString);
    }

    /**
     * Sends empty GET request.
     * @throws IOException
     */
    @Override
    public void sendRequest() throws IOException
    {
        try {
            URL url = new URL(this.url);
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestMethod("GET");
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
     * Sends GET request with parameters.
     * @param data GET parameters as Map object
     * @throws IOException
     */
    @Override
    public void sendRequest(Map<String, String> data) throws IOException
    {
        generateQuery(data);
        try {
            URL url = new URL(this.url + "?" + this.query);
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestMethod("GET");

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
}