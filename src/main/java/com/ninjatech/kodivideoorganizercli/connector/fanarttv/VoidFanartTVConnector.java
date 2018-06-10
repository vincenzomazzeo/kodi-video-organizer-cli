package com.ninjatech.kodivideoorganizercli.connector.fanarttv;

import java.net.URI;
import java.nio.file.Path;

import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.fanarttv.model.SerieImages;

public class VoidFanartTVConnector implements FanartTVConnector {

    protected VoidFanartTVConnector() {}

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public SerieImages serieImages(RestTemplate restTemplate, String serieId) {
        return null;
    }

    @Override
    public void downloadImage(RestTemplate restTemplate, URI uri, Path path) {}

}
