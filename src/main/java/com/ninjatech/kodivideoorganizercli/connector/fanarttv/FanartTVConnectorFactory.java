package com.ninjatech.kodivideoorganizercli.connector.fanarttv;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class FanartTVConnectorFactory {

    protected static final String URI = "http://webservice.fanart.tv/";
    private static final String API_KEY = "e838894004141b12e3a263972800187f";

    private FanartTVConnectorFactory() {}

    public static FanartTVConnector initialize(RestTemplate restTemplate, String api) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("api-key", FanartTVConnectorFactory.API_KEY);
        httpHeaders.add("client-key", api);

        return new BaseFanartTVConnector(httpHeaders);
    }

    public static FanartTVConnector reset() {
        return new VoidFanartTVConnector();
    }

}
