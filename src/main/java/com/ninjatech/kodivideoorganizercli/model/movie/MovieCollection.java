package com.ninjatech.kodivideoorganizercli.model.movie;

import java.nio.file.Path;

public class MovieCollection {

    private final String name;
    private final Path folder;

    public MovieCollection(String name,
                           Path folder) {
        this.name = name;
        this.folder = folder;
    }

    public String getName() {
        return this.name;
    }

    public Path getFolder() {
        return this.folder;
    }

}
