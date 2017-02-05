package com.chadrc.utils.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by chad on 2/4/17.
 */
public class Response {
    private final int code;
    private final String rawData;

    public Response(int code, String rawData) {
        this.code = code;
        this.rawData = rawData;
    }

    public <T> T getData(Class<T> type) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(rawData, type);
    }

    public int getCode() {
        return code;
    }
}
