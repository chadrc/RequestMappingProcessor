package com.chadrc.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chad on 2/4/17.
 */
public class ExternalRequest {
    private final String baseUrl;

    public ExternalRequest(String url) {
        this.baseUrl = url;

    }

    public Response Get() throws IOException, ClassCastException {
        URL url = new URL(baseUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new Response(responseCode, response.toString());
    }

    public Response Post(Object data) {
        return null;
    }

    public Response Put() {
        return null;
    }

    public Response Delete() {
        return null;
    }
}
