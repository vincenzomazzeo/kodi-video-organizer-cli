package com.ninjatech.kodivideoorganizercli.connector.themoviedb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieExternalId {

    private final Integer id;
    private final String imdbId;
    private final String facebookId;
    private final String instagramId;
    private final String twitterId;

    @JsonCreator
    private MovieExternalId(@JsonProperty("id") Integer id,
                            @JsonProperty("imdb_id") String imdbId,
                            @JsonProperty("facebook_id") String facebookId,
                            @JsonProperty("instagram_id") String instagramId,
                            @JsonProperty("twitter_id") String twitterId) {
        this.id = id;
        this.imdbId = imdbId;
        this.facebookId = facebookId;
        this.instagramId = instagramId;
        this.twitterId = twitterId;
    }

    public Integer getId() {
        return this.id;
    }

    public String getImdbId() {
        return this.imdbId;
    }

    public String getFacebookId() {
        return this.facebookId;
    }

    public String getInstagramId() {
        return this.instagramId;
    }

    public String getTwitterId() {
        return this.twitterId;
    }

}
