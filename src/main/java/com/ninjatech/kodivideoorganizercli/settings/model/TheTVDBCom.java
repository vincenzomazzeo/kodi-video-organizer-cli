package com.ninjatech.kodivideoorganizercli.settings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheTVDBCom {

    private static final String API = "api";
    private static final String LANGUAGE = "language";

    @JsonProperty(TheTVDBCom.API)
    private final String api;
    @JsonProperty(TheTVDBCom.LANGUAGE)
    private final String language;

    @JsonCreator
    public TheTVDBCom(
            @JsonProperty(TheTVDBCom.API) String api,
            @JsonProperty(TheTVDBCom.LANGUAGE) String language) {
        this.api = api;
        this.language = language;
    }

    public String getApi() {
        return this.api;
    }

    public String getLanguage() {
        return this.language;
    }

}
