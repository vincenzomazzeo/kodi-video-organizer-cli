package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Episode {

    private final Integer absoluteNumber;
    private final Integer airedEpisodeNumber;
    private final Integer airedSeason;
    private final Integer dvdEpisodeNumber;
    private final Integer dvdSeason;
    private final String episodeName;
    private final String firstAired;
    private final Integer id;
    private final Integer lastUpdated;
    private final String overview;

    @JsonCreator
    public Episode(@JsonProperty("absoluteNumber") Integer absoluteNumber,
                   @JsonProperty("airedEpisodeNumber") Integer airedEpisodeNumber,
                   @JsonProperty("airedSeason") Integer airedSeason,
                   @JsonProperty("dvdEpisodeNumber") Integer dvdEpisodeNumber,
                   @JsonProperty("dvdSeason") Integer dvdSeason,
                   @JsonProperty("episodeName") String episodeName,
                   @JsonProperty("firstAired") String firstAired,
                   @JsonProperty("id") Integer id,
                   @JsonProperty("lastUpdated") Integer lastUpdated,
                   @JsonProperty("overview") String overview) {
        this.absoluteNumber = absoluteNumber;
        this.airedEpisodeNumber = airedEpisodeNumber;
        this.airedSeason = airedSeason;
        this.dvdEpisodeNumber = dvdEpisodeNumber;
        this.dvdSeason = dvdSeason;
        this.episodeName = episodeName;
        this.firstAired = firstAired;
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.overview = overview;
    }

    public Integer getAbsoluteNumber() {
        return this.absoluteNumber;
    }

    public Integer getAiredEpisodeNumber() {
        return this.airedEpisodeNumber;
    }

    public Integer getAiredSeason() {
        return this.airedSeason;
    }

    public Integer getDvdEpisodeNumber() {
        return this.dvdEpisodeNumber;
    }

    public Integer getDvdSeason() {
        return this.dvdSeason;
    }

    public String getEpisodeName() {
        return this.episodeName;
    }

    public String getFirstAired() {
        return this.firstAired;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getLastUpdated() {
        return this.lastUpdated;
    }

    public String getOverview() {
        return this.overview;
    }

}
