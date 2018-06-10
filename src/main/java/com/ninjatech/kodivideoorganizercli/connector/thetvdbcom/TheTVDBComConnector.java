package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom;

import java.util.List;
import java.util.Locale;

import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Episode;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Serie;

public interface TheTVDBComConnector {

    public boolean isActive();

    public List<Serie> search(RestTemplate restTemplate, String query, Locale language);

    public List<String> seasons(RestTemplate restTemplate, String serieId);

    public List<Episode> episodes(RestTemplate restTemplate, String serieId, String seasonNumber, Locale language);

}
