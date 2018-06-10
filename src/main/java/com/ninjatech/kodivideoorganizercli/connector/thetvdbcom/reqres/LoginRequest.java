package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty
    private final String apikey;

    public LoginRequest(String apikey) {
        this.apikey = apikey;
    }

}
