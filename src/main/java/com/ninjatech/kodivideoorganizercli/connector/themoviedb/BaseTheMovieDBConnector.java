package com.ninjatech.kodivideoorganizercli.connector.themoviedb;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.MovieExternalId;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.reqres.GetByIdResponse;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.reqres.SearchMovieResponse;

public class BaseTheMovieDBConnector implements TheMovieDBConnector {

    private final String api;
    private final String language;

    protected BaseTheMovieDBConnector(String api,
                                      String language) {
        this.api = api;
        this.language = language;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public List<Movie> search(RestTemplate restTemplate, String query) {
        List<Movie> result = new LinkedList<>();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("api_key", Collections.singletonList(this.api));
        queryParams.put("language", Collections.singletonList(this.language));
        queryParams.put("query", Collections.singletonList(query));
        queryParams.put("include_adult", Collections.singletonList("false"));

        Integer page = 1;
        do {
            queryParams.put("page", Collections.singletonList(page.toString()));
            SearchMovieResponse response = get(restTemplate,
                                               SearchMovieResponse.class,
                                               new String[] { "search", "movie" },
                                               null,
                                               queryParams);
            result.addAll(response.getResult());
            page = response.getTotalPages() > page ? page + 1 : null;
        }
        while (page != null);

        return result;
    }

    @Override
    public MovieExternalId movieExternalId(RestTemplate restTemplate, Integer movieId) {
        MovieExternalId result = null;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("api_key", Collections.singletonList(this.api));

        result = get(restTemplate,
                     MovieExternalId.class,
                     new String[] { "movie", "{id}", "external_ids" },
                     Collections.singletonMap("id", movieId.toString()),
                     queryParams);

        return result;
    }

    @Override
    public Movie getByExternalId(RestTemplate restTemplate, String imdbId) {
        Movie result = null;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("api_key", Collections.singletonList(this.api));
        queryParams.put("language", Collections.singletonList(this.language));
        queryParams.put("external_source", Collections.singletonList("imdb_id"));

        GetByIdResponse response = get(restTemplate,
                                       GetByIdResponse.class,
                                       new String[] { "find", "{imdbId}" },
                                       Collections.singletonMap("imdbId", imdbId),
                                       queryParams);
        List<Movie> movies = response.getMovieResults();
        if (movies.size() == 1) {
            result = movies.get(0);
        }

        return result;
    }

    private <T> T get(RestTemplate restTemplate,
                      Class<T> responseType,
                      String[] pathSegments,
                      Map<String, String> pathParams,
                      MultiValueMap<String, String> queryParams) {
        T result = null;

        try {
            HttpEntity<Void> request = new HttpEntity<>(null);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(TheMovieDBConnectorFactory.URI)
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
