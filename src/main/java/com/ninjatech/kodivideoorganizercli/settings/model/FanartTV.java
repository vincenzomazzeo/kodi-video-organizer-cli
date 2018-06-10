package com.ninjatech.kodivideoorganizercli.settings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FanartTV {

    private static final String API = "api";

    @JsonProperty(FanartTV.API)
    private final String api;

    @JsonCreator
    public FanartTV(@JsonProperty(FanartTV.API) String api) {
        this.api = api;
    }

    public String getApi() {
        return this.api;
    }

}
