package com.ninjatech.kodivideoorganizercli.settings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Proxy {

    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @JsonProperty(Proxy.HOST)
    private final String host;
    @JsonProperty(Proxy.PORT)
    private final Integer port;
    @JsonProperty(Proxy.USERNAME)
    private final String username;
    @JsonProperty(Proxy.PASSWORD)
    private final String password;

    @JsonCreator
    public Proxy(
            @JsonProperty(Proxy.HOST) String host,
            @JsonProperty(Proxy.PORT) Integer port,
            @JsonProperty(Proxy.USERNAME) String username,
            @JsonProperty(Proxy.PASSWORD) String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
