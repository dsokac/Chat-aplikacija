package hr.foi.air.t18.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Danijel on 24.10.2015..
 */
public class HttpGET extends HttpMethod
{
    private String query = "";

    public HttpGET(String urlString)
    {
        super(urlString);
    }

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

    private void generateQuery(Map<String, String> data)
    {
        String query = "";
        for (String key : data.keySet()) {
            query += key + "=" + data.get(key) + "&";
        }
        this.query = query.substring(0, query.lastIndexOf("&"));
    }
}
