package com.ninjatech.kodivideoorganizercli.connector.fanarttv.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SerieImages {

    private final String name;
    private final String theTVDBComId;
    private final List<CharacterArt> characterArts;
    private final List<HDClearArt> hdClearArts;
    private final List<HDTVLogo> hdTVLogos;
    private final List<SeasonPoster> seasonPosters;
    private final List<ShowBackground> showBackgrounds;
    private final List<TVBanner> tvBanners;
    private final List<TVPoster> tvPosters;
    private final List<TVThumb> tvThumbs;

    @JsonCreator
    public SerieImages(@JsonProperty("name") String name,
                    @JsonProperty("thetvdb_id") String theTVDBComId,
                    @JsonProperty("characterart") List<CharacterArt> characterArts,
                    @JsonProperty("hdclearart") List<HDClearArt> hdClearArts,
                    @JsonProperty("hdtvlogo") List<HDTVLogo> hdTVLogos,
                    @JsonProperty("seasonposter") List<SeasonPoster> seasonPosters,
                    @JsonProperty("showbackground") List<ShowBackground> showBackgrounds,
                    @JsonProperty("tvbanner") List<TVBanner> tvBanners,
                    @JsonProperty("tvposter") List<TVPoster> tvPosters,
                    @JsonProperty("tvthumb") List<TVThumb> tvThumbs) {
        this.name = name;
        this.theTVDBComId = theTVDBComId;
        this.characterArts = characterArts;
        this.hdClearArts = hdClearArts;
        this.hdTVLogos = hdTVLogos;
        this.seasonPosters = seasonPosters;
        this.showBackgrounds = showBackgrounds;
        this.tvBanners = tvBanners;
        this.tvPosters = tvPosters;
        this.tvThumbs = tvThumbs;
    }

    public String getName() {
        return this.name;
    }

    public String getTheTVDBComId() {
        return this.theTVDBComId;
    }

    public List<CharacterArt> getCharacterArts() {
        return this.characterArts;
    }

    public List<HDClearArt> getHdClearArts() {
        return this.hdClearArts;
    }

    public List<HDTVLogo> getHdTVLogos() {
        return this.hdTVLogos;
    }

    public List<SeasonPoster> getSeasonPosters() {
        return this.seasonPosters;
    }

    public List<ShowBackground> getShowBackgrounds() {
        return this.showBackgrounds;
    }

    public List<TVBanner> getTvBanners() {
        return this.tvBanners;
    }

    public List<TVPoster> getTvPosters() {
        return this.tvPosters;
    }

    public List<TVThumb> getTvThumbs() {
        return this.tvThumbs;
    }

}
