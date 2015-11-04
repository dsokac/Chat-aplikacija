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
public class HttpPOST extends HttpMethod
{
    private String query = "";

    public HttpPOST(String url)
    {
        super(url);
    }

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

    @Override
    public void sendRequest(Map<String, String> data) throws IOException
    {
        generateQuery(data);
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
