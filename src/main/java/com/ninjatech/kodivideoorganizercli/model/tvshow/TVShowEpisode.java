package com.ninjatech.kodivideoorganizercli.model.tvshow;

import java.nio.file.Path;

public class TVShowEpisode implements Comparable<TVShowEpisode> {

    private final TVShowSeason season;
    private final Integer id;
    private final Integer number;
    private final String name;
    private Path file;

    protected TVShowEpisode(TVShowSeason season,
                            Integer id,
                            Integer number,
                            String name) {
        this.season = season;
        this.id = id;
        this.number = number;
        this.name = name;
    }

    @Override
    public int compareTo(TVShowEpisode other) {
        return this.number.compareTo(other.number);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TVShowEpisode other = (TVShowEpisode) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public TVShowSeason getSeason() {
        return this.season;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }

    public Path getFile() {
        return this.file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

}
