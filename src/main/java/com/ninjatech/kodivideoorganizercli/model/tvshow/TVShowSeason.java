package com.ninjatech.kodivideoorganizercli.model.tvshow;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TVShowSeason implements Comparable<TVShowSeason> {

    private final TVShow tvShow;
    private final Integer number;
    private final String name;
    private final Path folder;
    private final List<TVShowEpisode> episodes;

    protected TVShowSeason(TVShow tvShow,
                           Integer number,
                           String name,
                           Path folder) {
        this.tvShow = tvShow;
        this.number = number;
        this.name = name;
        this.folder = folder;
        this.episodes = new LinkedList<>();
    }

    @Override
    public int compareTo(TVShowSeason other) {
        return this.number.compareTo(other.number);
    }

    public TVShow getTvShow() {
        return this.tvShow;
    }

    public Integer getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }

    public Path getFolder() {
        return this.folder;
    }

    public TVShowEpisode addEpisode(Integer id, Integer number, String name) {
        TVShowEpisode result = new TVShowEpisode(this, id, number, name);

        this.episodes.add(result);
        Collections.sort(this.episodes);

        return result;
    }

    public List<TVShowEpisode> getEpisodes() {
        return this.episodes;
    }

}
