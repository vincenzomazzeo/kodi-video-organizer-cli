package com.ninjatech.kodivideoorganizercli.connector.fanarttv;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ninjatech.kodivideoorganizercli.connector.fanarttv.model.SerieImages;

public class BaseFanartTVConnector implements FanartTVConnector {

    private final HttpHeaders httpHeaders;

    protected BaseFanartTVConnector(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public SerieImages serieImages(RestTemplate restTemplate, String serieId) {
        return get(restTemplate,
                   SerieImages.class,
                   new String[] { "v3", "tv", "{id}" },
                   Collections.singletonMap("id", serieId),
                   null);
    }

    @Override
    public void downloadImage(RestTemplate restTemplate, URI uri, final Path path) {
        restTemplate.execute(uri, HttpMethod.GET, request -> {}, response -> Files.copy(response.getBody(), path));
    }

    private <T> T get(RestTemplate restTemplate,
                      Class<T> responseType,
                      String[] pathSegments,
                      Map<String, String> pathParams,
                      MultiValueMap<String, String> queryParams) {
        T result = null;

        try {
            HttpEntity<Void> request = new HttpEntity<>(this.httpHeaders);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(FanartTVConnectorFactory.URI)
                                                               .pathSegment(pathSegments);
            if (queryParams != null) {
                builder.queryParams(queryParams);
            }
            UriComponents uriComponents = pathParams != null ? builder.buildAndExpand(pathParams) : builder.build();
            ResponseEntity<T> response = restTemplate.exchange(uriComponents.encode()
                                                                            .toUri(),
                                                               HttpMethod.GET,
                                                               request,
                                                               responseType);
            result = response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw e;
            }
        }

        return result;
    }

}
