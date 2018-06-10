package com.ninjatech.kodivideoorganizercli.connector.themoviedb;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.MovieExternalId;

public interface TheMovieDBConnector {

    public boolean isActive();

    public List<Movie> search(RestTemplate restTemplate, String query);

    public MovieExternalId movieExternalId(RestTemplate restTemplate, Integer movieId);

    public Movie getByExternalId(RestTemplate restTemplate, String imdbId);

}
