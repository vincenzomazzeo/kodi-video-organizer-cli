package com.ninjatech.kodivideoorganizercli.connector.themoviedb;

import org.springframework.web.client.RestTemplate;

public class TheMovieDBConnectorFactory {

    protected static final String URI = "https://api.themoviedb.org/3/";

    private TheMovieDBConnectorFactory() {}

    public static TheMovieDBConnector initialize(RestTemplate restTemplate, String api, String language) {
        return new BaseTheMovieDBConnector(api, language);
    }

    public static TheMovieDBConnector reset() {
        return new VoidTheMovieDBConnector();
    }

}
