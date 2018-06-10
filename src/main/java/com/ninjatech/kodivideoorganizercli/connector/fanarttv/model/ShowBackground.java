package com.ninjatech.kodivideoorganizercli.connector.fanarttv.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowBackground {

    private final String id;
    private final String url;
    private final String language;
    private final String likes;
    private final String season;

    @JsonCreator
    private ShowBackground(@JsonProperty("id") String id,
                           @JsonProperty("url") String url,
                           @JsonProperty("lang") String language,
                           @JsonProperty("likes") String likes,
                           @JsonProperty("season") String season) {
        this.id = id;
        this.url = url;
        this.language = language;
        this.likes = likes;
        this.season = season;
    }

    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getLikes() {
        return this.likes;
    }

    public String getSeason() {
        return this.season;
    }

}
