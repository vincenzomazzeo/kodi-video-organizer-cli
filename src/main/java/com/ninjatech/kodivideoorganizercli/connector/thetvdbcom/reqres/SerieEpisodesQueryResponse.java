package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Episode;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Links;

public class SerieEpisodesQueryResponse {

    private final List<Episode> episodes;
    private final Links links;

    @JsonCreator
    public SerieEpisodesQueryResponse(@JsonProperty("data") List<Episode> episodes,
                                      @JsonProperty("links") Links links) {
        this.episodes = episodes;
        this.links = links;
    }

    public List<Episode> getEpisodes() {
        return this.episodes;
    }

    public Links getLinks() {
        return this.links;
    }

}
