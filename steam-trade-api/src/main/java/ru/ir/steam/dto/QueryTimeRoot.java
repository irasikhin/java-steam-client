package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class QueryTimeRoot implements Serializable {

    @JsonProperty("response")
    private QueryTimeResponse response;

    public QueryTimeResponse getResponse() {
        return response;
    }

    public void setResponse(QueryTimeResponse response) {
        this.response = response;
    }
}
