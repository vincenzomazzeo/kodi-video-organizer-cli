package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EpisodesSummary {

    private final String airedEpisodes;
    private final List<String> airedSeasons;
    private final String dvdEpisodes;
    private final List<String> dvdSeasons;

    @JsonCreator
    private EpisodesSummary(@JsonProperty("airedEpisodes") String airedEpisodes,
                                  @JsonProperty("airedSeasons") List<String> airedSeasons,
                                  @JsonProperty("dvdEpisodes") String dvdEpisodes,
                                  @JsonProperty("dvdSeasons") List<String> dvdSeasons) {
        this.airedEpisodes = airedEpisodes;
        this.airedSeasons = airedSeasons;
        this.dvdEpisodes = dvdEpisodes;
        this.dvdSeasons = dvdSeasons;
    }

    public String getAiredEpisodes() {
        return this.airedEpisodes;
    }

    public List<String> getAiredSeasons() {
        return this.airedSeasons;
    }

    public String getDvdEpisodes() {
        return this.dvdEpisodes;
    }

    public List<String> getDvdSeasons() {
        return this.dvdSeasons;
    }

}
