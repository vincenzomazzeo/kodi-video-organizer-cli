package com.ninjatech.kodivideoorganizercli.connector.themoviedb;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.MovieExternalId;

public class VoidTheMovieDBConnector implements TheMovieDBConnector {

    protected VoidTheMovieDBConnector() {}

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public List<Movie> search(RestTemplate restTemplate, String query) {
        return null;
    }

    @Override
    public MovieExternalId movieExternalId(RestTemplate restTemplate, Integer movieId) {
        return null;
    }

    @Override
    public Movie getByExternalId(RestTemplate restTemplate, String imdbId) {
        return null;
    }

}
