package com.ninjatech.kodivideoorganizercli.connector.themoviedb.reqres;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie;

public class SearchMovieResponse {

    private final Integer page;
    private final Integer totalResults;
    private final Integer totalPages;
    private final List<Movie> result;

    @JsonCreator
    public SearchMovieResponse(@JsonProperty("page") Integer page,
                               @JsonProperty("total_results") Integer totalResults,
                               @JsonProperty("total_pages") Integer totalPages,
                               @JsonProperty("results") List<Movie> result) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.result = result;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getTotalResults() {
        return this.totalResults;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public List<Movie> getResult() {
        return this.result;
    }

}
