package com.ninjatech.kodivideoorganizercli.settings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheMovieDB {

    private static final String API = "api";
    private static final String LANGUAGE = "language";

    @JsonProperty(TheMovieDB.API)
    private final String api;
    @JsonProperty(TheMovieDB.LANGUAGE)
    private final String language;

    @JsonCreator
    public TheMovieDB(@JsonProperty(TheMovieDB.API) String api,
                      @JsonProperty(TheMovieDB.LANGUAGE) String language) {
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
