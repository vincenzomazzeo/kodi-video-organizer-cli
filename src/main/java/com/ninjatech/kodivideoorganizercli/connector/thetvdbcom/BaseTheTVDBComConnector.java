package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Episode;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Serie;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres.SearchSeriesResponse;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres.SerieEpisodesQueryResponse;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres.SerieEpisodesSummaryResponse;

public class BaseTheTVDBComConnector implements TheTVDBComConnector {

    private final HttpHeaders httpHeaders;

    protected BaseTheTVDBComConnector(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public List<Serie> search(RestTemplate restTemplate, String query, Locale language) {
        this.httpHeaders.setAcceptLanguageAsLocales(Collections.singletonList(language));

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("name", Collections.singletonList(query));
        SearchSeriesResponse response = get(restTemplate,
                                            SearchSeriesResponse.class,
                                            new String[] { "search", "series" },
                                            null,
                                            queryParams);
        return response != null ? response.getSeries() : Collections.emptyList();
    }

    @Override
    public List<String> seasons(RestTemplate restTemplate, String serieId) {
        SerieEpisodesSummaryResponse response = get(restTemplate,
                                                    SerieEpisodesSummaryResponse.class,
                                                    new String[] { "series", "{id}", "episodes", "summary" },
                                                    Collections.singletonMap("id", serieId),
                                                    null);
        return response != null ? response.getEpisodesSummary()
                                          .getAiredSeasons()
                                : Collections.emptyList();
    }

    @Override
    public List<Episode> episodes(RestTemplate restTemplate, String serieId, String seasonNumber, Locale language) {
        List<Episode> result = new LinkedList<>();

        this.httpHeaders.setAcceptLanguageAsLocales(Collections.singletonList(language));

        Integer next = 1;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("airedSeason", Collections.singletonList(seasonNumber));
        do {
            queryParams.put("page", Collections.singletonList(next.toString()));
            SerieEpisodesQueryResponse response = get(restTemplate,
                                                      SerieEpisodesQueryResponse.class,
                                                      new String[] { "series", "{id}", "episodes", "query" },
                                                      Collections.singletonMap("id", serieId),
                                                      queryParams);
            result.addAll(response.getEpisodes());
            next = response.getLinks()
                           .getNext();
        }
        while (next != null);

        return result;
    }

    private <T> T get(RestTemplate restTemplate,
                      Class<T> responseType,
                      String[] pathSegments,
                      Map<String, String> pathParams,
                      MultiValueMap<String, String> queryParams) {
        T result = null;

        try {
            HttpEntity<Void> request = new HttpEntity<>(this.httpHeaders);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(TheTVDBComConnectorFactory.URI)
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
