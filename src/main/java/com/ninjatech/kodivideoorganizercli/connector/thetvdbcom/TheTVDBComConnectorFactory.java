package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres.LoginRequest;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.reqres.LoginResponse;

public class TheTVDBComConnectorFactory {

    protected static final String URI = "https://api.thetvdb.com/";

    private TheTVDBComConnectorFactory() {}

    public static TheTVDBComConnector connect(RestTemplate restTemplate, String api) {
        TheTVDBComConnector result = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<LoginRequest> request = new HttpEntity<>(new LoginRequest(api), httpHeaders);

        try {
            ResponseEntity<LoginResponse> response = restTemplate.exchange(TheTVDBComConnectorFactory.URI.concat("login"),
                                                                           HttpMethod.POST,
                                                                           request,
                                                                           LoginResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                httpHeaders.set("Authorization",
                                String.format("Bearer %s",
                                              response.getBody()
                                                      .getToken()));
                result = new BaseTheTVDBComConnector(httpHeaders);
            }
            else {
                result = new VoidTheTVDBComConnector();
            }
        }
        catch (ResourceAccessException e) {
            result = new VoidTheTVDBComConnector();
            e.printStackTrace();
        }

        return result;
    }

    public static TheTVDBComConnector reset() {
        return new VoidTheTVDBComConnector();
    }

}
