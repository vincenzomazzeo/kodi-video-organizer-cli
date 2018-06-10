package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    private final String token;

    @JsonCreator
    private LoginResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
