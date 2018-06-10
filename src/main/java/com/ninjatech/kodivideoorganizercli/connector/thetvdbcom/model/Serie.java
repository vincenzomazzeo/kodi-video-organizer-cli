package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Serie {

    private final List<String> aliases;
    private final String banner;
    private final String firstAired;
    private final Integer id;
    private final String network;
    private final String overview;
    private final String seriesName;
    private final String status;

    @JsonCreator
    private Serie(
            @JsonProperty("aliases") List<String> aliases,
            @JsonProperty("banner") String banner,
            @JsonProperty("firstAired") String firstAired,
            @JsonProperty("id") Integer id,
            @JsonProperty("network") String network,
            @JsonProperty("overview") String overview,
            @JsonProperty("seriesName") String seriesName,
            @JsonProperty("status") String status) {
        this.aliases = aliases;
        this.banner = banner;
        this.firstAired = firstAired;
        this.id = id;
        this.network = network;
        this.overview = overview;
        this.seriesName = seriesName;
        this.status = status;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public String getBanner() {
        return this.banner;
    }

    public String getFirstAired() {
        return this.firstAired;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNetwork() {
        return this.network;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getSeriesName() {
        return this.seriesName;
    }

    public String getStatus() {
        return this.status;
    }

}
