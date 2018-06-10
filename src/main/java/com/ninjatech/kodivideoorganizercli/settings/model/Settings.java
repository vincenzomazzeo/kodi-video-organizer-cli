package com.ninjatech.kodivideoorganizercli.settings.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

    @JsonProperty
    private Proxy proxy;
    @JsonProperty
    private TheTVDBCom theTVDBCom;
    @JsonProperty
    private FanartTV fanartTV;
    @JsonProperty
    private TheMovieDB theMovieDB;

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public TheTVDBCom getTheTVDBCom() {
        return this.theTVDBCom;
    }

    public void setTheTVDBCom(TheTVDBCom theTVDBCom) {
        this.theTVDBCom = theTVDBCom;
    }

    public FanartTV getFanartTV() {
        return this.fanartTV;
    }

    public void setFanartTV(FanartTV fanartTV) {
        this.fanartTV = fanartTV;
    }

    public TheMovieDB getTheMovieDB() {
        return this.theMovieDB;
    }

    public void setTheMovieDB(TheMovieDB theMovieDB) {
        this.theMovieDB = theMovieDB;
    }

}
