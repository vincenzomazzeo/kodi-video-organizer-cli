package com.ninjatech.kodivideoorganizercli.connector.themoviedb.reqres;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie;

public class GetByIdResponse {

    private final List<Movie> movieResults;

    @JsonCreator
    public GetByIdResponse(@JsonProperty("movie_results") List<Movie> movieResults) {
        this.movieResults = movieResults;
    }

    public List<Movie> getMovieResults() {
        return this.movieResults;
    }

}
