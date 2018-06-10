package com.ninjatech.kodivideoorganizercli.connector.fanarttv;

import java.net.URI;
import java.nio.file.Path;

import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.fanarttv.model.SerieImages;

public interface FanartTVConnector {

    public boolean isActive();

    public SerieImages serieImages(RestTemplate restTemplate, String serieId);

    public void downloadImage(RestTemplate restTemplate, URI uri, Path path);

}
