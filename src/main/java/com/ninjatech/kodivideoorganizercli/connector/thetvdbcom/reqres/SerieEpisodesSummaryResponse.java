package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.EpisodesSummary;

public class SerieEpisodesSummaryResponse {

    private final EpisodesSummary episodesSummary;

    @JsonCreator
    private SerieEpisodesSummaryResponse(@JsonProperty("data") EpisodesSummary episodesSummary) {
        this.episodesSummary = episodesSummary;
    }

    public EpisodesSummary getEpisodesSummary() {
        return this.episodesSummary;
    }

}
