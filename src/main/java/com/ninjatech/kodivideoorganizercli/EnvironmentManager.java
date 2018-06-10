package com.ninjatech.kodivideoorganizercli;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ninjatech.kodivideoorganizercli.connector.fanarttv.FanartTVConnector;
import com.ninjatech.kodivideoorganizercli.connector.fanarttv.FanartTVConnectorFactory;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.TheMovieDBConnector;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.TheMovieDBConnectorFactory;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.TheTVDBComConnector;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.TheTVDBComConnectorFactory;
import com.ninjatech.kodivideoorganizercli.model.movie.Movie;
import com.ninjatech.kodivideoorganizercli.model.movie.MovieCollection;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.settings.SettingsHandler;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class EnvironmentManager {

    public static final Path BASE_PATH = new File(System.getProperty("user.home")).toPath()
                                                                                  .resolve(".kvocli");

    private final OutputComponent outputComponent;
    private final SettingsHandler settingsHandler;
    private final SSLConnectionSocketFactory sslConnectionSocketFactory;
    private final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory;
    private RestTemplate restTemplate;
    private Path basePath;
    private TheTVDBComConnector theTVDBComConnector;
    private FanartTVConnector fanartTVConnector;
    private TheMovieDBConnector theMovieDBConnector;
    private TVShow selectedTVShow;
    private MovieCollection selectedMovieCollection;
    private Movie selectedMovie;

    public EnvironmentManager(OutputComponent outputComponent,
                              SettingsHandler settingsHandler,
                              SSLConnectionSocketFactory sslConnectionSocketFactory) {
        this.outputComponent = outputComponent;
        this.settingsHandler = settingsHandler;
        this.sslConnectionSocketFactory = sslConnectionSocketFactory;
        this.httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        if (this.settingsHandler.getProxy() != null) {
            com.ninjatech.kodivideoorganizercli.settings.model.Proxy proxy = this.settingsHandler.getProxy();
            setProxy(proxy.getHost(), proxy.getPort(), proxy.getUsername(), proxy.getPassword());
        }
        else {
            unsetProxy();
        }

        if (this.settingsHandler.getTheTVDBCom() != null) {
            this.theTVDBComConnector = TheTVDBComConnectorFactory.connect(this.restTemplate,
                                                                          this.settingsHandler.getTheTVDBCom()
                                                                                              .getApi());
            this.outputComponent.appendSystemLine(this.theTVDBComConnector.isActive() ? "# TheTVDB.com is active"
                                                                                      : "# TheTVDB.com is not active");
        }
        else {
            this.theTVDBComConnector = TheTVDBComConnectorFactory.reset();
            this.outputComponent.appendSystemLine("# TheTVDB.com is not active");
        }

        if (this.settingsHandler.getFanartTV() != null) {
            this.fanartTVConnector = FanartTVConnectorFactory.initialize(this.restTemplate,
                                                                         this.settingsHandler.getFanartTV()
                                                                                             .getApi());
            this.outputComponent.appendSystemLine(this.fanartTVConnector.isActive() ? "# FanartTV is active"
                                                                                    : "# FanartTV is not active");
        }
        else {
            this.fanartTVConnector = FanartTVConnectorFactory.reset();
            this.outputComponent.appendSystemLine("# FanartTV is not active");
        }

        if (this.settingsHandler.getTheMovieDB() != null) {
            this.theMovieDBConnector = TheMovieDBConnectorFactory.initialize(this.restTemplate,
                                                                             this.settingsHandler.getTheMovieDB()
                                                                                                 .getApi(),
                                                                             this.settingsHandler.getTheMovieDB()
                                                                                                 .getLanguage());
            this.outputComponent.appendSystemLine(this.theMovieDBConnector.isActive() ? "# TheMovieDB is active"
                                                                                      : "# TheMovieDB is not active");
        }
        else {
            this.theMovieDBConnector = TheMovieDBConnectorFactory.reset();
            this.outputComponent.appendSystemLine("# TheMovieDB is not active");
        }
    }

    public HttpClientBuilder getBaseHttpClientBuilder() {
        return HttpClientBuilder.create()
                                .setSSLSocketFactory(this.sslConnectionSocketFactory);
    }

    public void setProxy(String host, Integer port, String username, String password) {
        HttpHost proxy = new HttpHost(host, port, "http");
        this.outputComponent.appendSystemLine("# Proxy set to %s:%d", host, port);
        HttpClientBuilder httpClientBuilder = getBaseHttpClientBuilder().setProxy(proxy);
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(proxy.getHostName(), proxy.getPort()),
                                               new UsernamePasswordCredentials(username, password));
            httpClientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                             .setDefaultCredentialsProvider(credentialsProvider);
            this.outputComponent.appendSystemLine("# Proxy credentials set to %s (****)", username);
        }
        this.httpComponentsClientHttpRequestFactory.setHttpClient(httpClientBuilder.build());
        this.restTemplate = new RestTemplate(this.httpComponentsClientHttpRequestFactory);
        this.settingsHandler.setProxy(host, port, username, password);
        if (this.settingsHandler.getTheTVDBCom() != null) {
            setTheTVDBComData(this.settingsHandler.getTheTVDBCom()
                                                  .getApi(),
                              this.settingsHandler.getTheTVDBCom()
                                                  .getLanguage());
        }
        if (this.settingsHandler.getFanartTV() != null) {
            setFanartTVData(this.settingsHandler.getFanartTV()
                                                .getApi());
        }
    }

    public void unsetProxy() {
        this.httpComponentsClientHttpRequestFactory.setHttpClient(getBaseHttpClientBuilder().build());
        this.restTemplate = new RestTemplate(this.httpComponentsClientHttpRequestFactory);
        this.settingsHandler.unsetProxy();
        this.outputComponent.appendSystemLine("# Proxy unset");
        if (this.settingsHandler.getTheTVDBCom() != null) {
            setTheTVDBComData(this.settingsHandler.getTheTVDBCom()
                                                  .getApi(),
                              this.settingsHandler.getTheTVDBCom()
                                                  .getLanguage());
        }
        if (this.settingsHandler.getFanartTV() != null) {
            setFanartTVData(this.settingsHandler.getFanartTV()
                                                .getApi());
        }
    }

    public Path getBasePath() {
        return this.basePath;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public void setTheTVDBComData(String api, String language) {
        this.theTVDBComConnector = TheTVDBComConnectorFactory.connect(this.restTemplate,
                                                                      api);
        if (this.theTVDBComConnector.isActive()) {
            this.settingsHandler.setTheTVDBCom(api, language);
            this.outputComponent.appendSystemLine("# TheTVDB.com data set and TheTVDB.com is active");
        }
        else {
            this.outputComponent.appendSystemLine("# TheTVDB.com data is not valid");
        }
    }

    public void unsetTheTVDBComData() {
        this.theTVDBComConnector = TheTVDBComConnectorFactory.reset();
        this.settingsHandler.unsetTheTVDBCom();
        this.outputComponent.appendSystemLine("# TheTVDB.com data unset and TheTVDB.com is inactive");
    }

    public TheTVDBComConnector getTheTVDBComConnector() {
        return this.theTVDBComConnector;
    }

    public void setFanartTVData(String api) {
        this.fanartTVConnector = FanartTVConnectorFactory.initialize(this.restTemplate, api);
        if (this.fanartTVConnector.isActive()) {
            this.settingsHandler.setFanartTV(api);
            this.outputComponent.appendSystemLine("# FanartTV data set and FanartTV is active");
        }
        else {
            this.outputComponent.appendSystemLine("# FanartTV data is not valid");
        }
    }

    public void unsetFanartTVData() {
        this.fanartTVConnector = FanartTVConnectorFactory.reset();
        this.settingsHandler.unsetFanartTV();
        this.outputComponent.appendSystemLine("# FanartTV data unset and FanartTV is inactive");
    }

    public FanartTVConnector getFanartTVConnector() {
        return this.fanartTVConnector;
    }

    public void setTheMovieDBData(String api, String language) {
        this.theMovieDBConnector = TheMovieDBConnectorFactory.initialize(this.restTemplate, api, language);
        if (this.theMovieDBConnector.isActive()) {
            this.settingsHandler.setTheMovieDB(api, language);
            this.outputComponent.appendSystemLine("# TheMovieDB data set and TheMovieDB is active");
        }
        else {
            this.outputComponent.appendSystemLine("# TheMovieDB data is not valid");
        }
    }

    public void unsetTheMovieDBData() {
        this.theMovieDBConnector = TheMovieDBConnectorFactory.reset();
        this.settingsHandler.unsetTheMovieDB();
        this.outputComponent.appendSystemLine("# TheMovieDB data unset and TheMovieDB is inactive");
    }

    public TheMovieDBConnector getTheMovieDBConnector() {
        return this.theMovieDBConnector;
    }

    public void selectTVShow(TVShow tvShow) {
        this.selectedTVShow = tvShow;
    }

    public void resetTVShow() {
        this.selectedTVShow = null;
    }

    public TVShow getSelectedTVShow() {
        return this.selectedTVShow;
    }

    public void selectMovieCollection(MovieCollection movieCollection) {
        this.selectedMovieCollection = movieCollection;
    }

    public void resetMovieCollection() {
        this.selectedMovieCollection = null;
    }

    public MovieCollection getSelectedMovieCollection() {
        return this.selectedMovieCollection;
    }

    public void selectMovie(Movie movie) {
        this.selectedMovie = movie;
    }

    public void resetMovie() {
        this.selectedMovie = null;
    }

    public Movie getSelectedMovie() {
        return this.selectedMovie;
    }

}
