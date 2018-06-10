package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom;

import java.util.List;
import java.util.Locale;

import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Episode;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Serie;

public class VoidTheTVDBComConnector implements TheTVDBComConnector {

    protected VoidTheTVDBComConnector() {}

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public List<Serie> search(RestTemplate restTemplate, String query, Locale language) {
        return null;
    }

    @Override
    public List<String> seasons(RestTemplate restTemplate, String serieId) {
        return null;
    }

    @Override
    public List<Episode> episodes(RestTemplate restTemplate, String serieId, String seasonNumber, Locale language) {
        return null;
    }

}
