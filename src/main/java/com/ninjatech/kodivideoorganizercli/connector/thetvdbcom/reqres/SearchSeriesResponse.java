package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Serie;

public class SearchSeriesResponse {

    private final List<Serie> series;

    @JsonCreator
    private SearchSeriesResponse(@JsonProperty("data") List<Serie> series) {
        this.series = series;
    }

    public List<Serie> getSeries() {
        return this.series;
    }

}