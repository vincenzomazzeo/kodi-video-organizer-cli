package com.ninjatech.kodivideoorganizercli.model.tvshow;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

public class TVShow {

    private final Integer id;
    private final String name;
    private final String firstAired;
    private final String status;
    private final List<String> banners;
    private final List<String> characters;
    private final List<String> cleararts;
    private final List<String> fanarts;
    private final List<String> landscapes;
    private final List<String> logos;
    private final List<String> posters;
    private final List<TVShowSeason> seasons;
    private final Map<Integer, List<String>> seasonPosters;
    private Path folder;
    private TVShowSeason selectedSeason;

    public TVShow(Integer id,
                  String name,
                  String firstAired,
                  String status) {
        this.id = id;
        this.name = name;
        this.firstAired = firstAired;
        this.status = status;
        this.banners = new LinkedList<>();
        this.characters = new LinkedList<>();
        this.cleararts = new LinkedList<>();
        this.fanarts = new LinkedList<>();
        this.landscapes = new LinkedList<>();
        this.logos = new LinkedList<>();
        this.posters = new LinkedList<>();
        this.seasons = new LinkedList<>();
        this.seasonPosters = new HashMap<>();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFirstAired() {
        return this.firstAired;
    }

    public String getStatus() {
        return this.status;
    }

    public Path getFolder() {
        return this.folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }

    public void addBanner(String url) {
        this.banners.add(url);
    }

    public List<String> getBanners() {
        return this.banners;
    }

    public void addCharacter(String url) {
        this.characters.add(url);
    }

    public List<String> getCharacters() {
        return this.characters;
    }

    public void addClearart(String url) {
        this.cleararts.add(url);
    }

    public List<String> getCleararts() {
        return this.cleararts;
    }

    public void addFanart(String url) {
        this.fanarts.add(url);
    }

    public List<String> getFanarts() {
        return this.fanarts;
    }

    public void addLandscape(String url) {
        this.landscapes.add(url);
    }

    public List<String> getLandscapes() {
        return this.landscapes;
    }

    public void addLogo(String url) {
        this.logos.add(url);
    }

    public List<String> getLogos() {
        return this.logos;
    }

    public void addPoster(String url) {
        this.posters.add(url);
    }

    public List<String> getPosters() {
        return this.posters;
    }

    public void addSeason(String number) {
        String seasonName = String.format(TVShowUtils.SEASON_FOLDER_FORMAT, number);
        this.seasons.add(new TVShowSeason(this,
                                          Integer.valueOf(number),
                                          seasonName,
                                          this.folder.resolve(seasonName)));
        Collections.sort(this.seasons);
    }

    public List<TVShowSeason> getSeasons() {
        return this.seasons;
    }

    public void addSeasonImage(Integer number, String url) {
        List<String> images = this.seasonPosters.get(number);
        if (images == null) {
            images = new LinkedList<>();
            this.seasonPosters.put(number, images);
        }
        images.add(url);
    }

    public List<String> getSeasonPoster(Integer number) {
        return this.seasonPosters.containsKey(number) ? this.seasonPosters.get(number) : Collections.emptyList();
    }

    public void selectSeason(TVShowSeason season) {
        this.selectedSeason = season;
    }

    public void deselectSeason() {
        this.selectedSeason = null;
    }

    public TVShowSeason getSelectedSeason() {
        return this.selectedSeason;
    }

}
