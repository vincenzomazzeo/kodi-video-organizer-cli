package com.ninjatech.kodivideoorganizercli.settings;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjatech.kodivideoorganizercli.settings.model.FanartTV;
import com.ninjatech.kodivideoorganizercli.settings.model.Proxy;
import com.ninjatech.kodivideoorganizercli.settings.model.Settings;
import com.ninjatech.kodivideoorganizercli.settings.model.TheMovieDB;
import com.ninjatech.kodivideoorganizercli.settings.model.TheTVDBCom;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class SettingsHandler {

    private final OutputComponent outputComponent;
    private final ObjectMapper objectMapper;
    private final File settingsFile;
    private final Settings settings;

    public SettingsHandler(OutputComponent outputComponent,
                           ObjectMapper objectMapper) throws IOException {
        this.outputComponent = outputComponent;
        this.objectMapper = objectMapper;
        File userHome = new File(System.getProperty("user.home"));
        this.settingsFile = new File(userHome, ".kvocli.settings");
        if (!this.settingsFile.exists()) {
            if (!this.settingsFile.createNewFile()) {
                throw new IOException("Failed to create settings file");
            }
            this.settings = new Settings();
            writeSettings();
        }
        else {
            this.settings = this.objectMapper.readValue(this.settingsFile, Settings.class);
        }
    }

    public Proxy getProxy() {
        return this.settings.getProxy();
    }

    public void setProxy(String host, Integer port, String username, String password) {
        Proxy proxy = new Proxy(host, port, username, password);
        this.settings.setProxy(proxy);
        writeSettings();
    }

    public void unsetProxy() {
        this.settings.setProxy(null);
        writeSettings();
    }

    public TheTVDBCom getTheTVDBCom() {
        return this.settings.getTheTVDBCom();
    }

    public void setTheTVDBCom(String api, String language) {
        TheTVDBCom theTVDBCom = new TheTVDBCom(api, language);
        this.settings.setTheTVDBCom(theTVDBCom);
        writeSettings();
    }

    public void unsetTheTVDBCom() {
        this.settings.setTheTVDBCom(null);
        writeSettings();
    }

    public FanartTV getFanartTV() {
        return this.settings.getFanartTV();
    }

    public void setFanartTV(String api) {
        FanartTV fanartTV = new FanartTV(api);
        this.settings.setFanartTV(fanartTV);
        writeSettings();
    }

    public void unsetFanartTV() {
        this.settings.setFanartTV(null);
        writeSettings();
    }

    public TheMovieDB getTheMovieDB() {
        return this.settings.getTheMovieDB();
    }

    public void setTheMovieDB(String api, String language) {
        TheMovieDB theMovieDB = new TheMovieDB(api, language);
        this.settings.setTheMovieDB(theMovieDB);
        writeSettings();
    }

    public void unsetTheMovieDB() {
        this.settings.setTheMovieDB(null);
        writeSettings();
    }

    private void writeSettings() {
        try {
            this.objectMapper.writeValue(this.settingsFile, this.settings);
        }
        catch (Exception e) {
            this.outputComponent.appendError(e);
            e.printStackTrace();
        }
    }

}
