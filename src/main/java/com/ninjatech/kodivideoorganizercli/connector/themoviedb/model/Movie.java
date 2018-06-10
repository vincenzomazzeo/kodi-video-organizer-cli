package com.ninjatech.kodivideoorganizercli.connector.themoviedb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    private final Integer id;
    private final Integer voteCount;
    private final Boolean video;
    private final Integer voteAverage;
    private final String title;
    private final Double popularity;
    private final String posterPath;
    private final String originalLanguage;
    private final String originalTitle;
    private final List<Integer> genreIds;
    private final String backdropPath;
    private final Boolean adult;
    private final String overview;
    private final String releaseDate;

    @JsonCreator
    public Movie(@JsonProperty("id") Integer id,
                 @JsonProperty("vote_count") Integer voteCount,
                 @JsonProperty("video") Boolean video,
                 @JsonProperty("vote_average") Integer voteAverage,
                 @JsonProperty("title") String title,
                 @JsonProperty("popularity") Double popularity,
                 @JsonProperty("poster_path") String posterPath,
                 @JsonProperty("original_language") String originalLanguage,
                 @JsonProperty("original_title") String originalTitle,
                 @JsonProperty("genre_ids") List<Integer> genreIds,
                 @JsonProperty("backdrop_path") String backdropPath,
                 @JsonProperty("adult") Boolean adult,
                 @JsonProperty("overview") String overview,
                 @JsonProperty("release_date") String releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getVoteCount() {
        return this.voteCount;
    }

    public Boolean getVideo() {
        return this.video;
    }

    public Integer getVoteAverage() {
        return this.voteAverage;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getPopularity() {
        return this.popularity;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public List<Integer> getGenreIds() {
        return this.genreIds;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public Boolean getAdult() {
        return this.adult;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

}
