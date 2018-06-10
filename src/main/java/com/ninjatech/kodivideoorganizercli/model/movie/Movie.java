package com.ninjatech.kodivideoorganizercli.model.movie;

import java.nio.file.Path;

public class Movie {

    private final Integer id;
    private final String title;
    private final String year;
    private final String imdbId;
    private String outline;
    private String plot;
    private String sortTitle;
    private String set;
    private Path folder;

    public Movie(Integer id,
                 String title,
                 String year,
                 String outline,
                 String plot,
                 String imdbId) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.outline = outline;
        this.plot = plot;
        this.imdbId = imdbId;
    }

    public String getOutline() {
        return this.outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getPlot() {
        return this.plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getSortTitle() {
        return this.sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }

    public String getSet() {
        return this.set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public Path getFolder() {
        return this.folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getYear() {
        return this.year;
    }

    public String getImdbId() {
        return this.imdbId;
    }

}
