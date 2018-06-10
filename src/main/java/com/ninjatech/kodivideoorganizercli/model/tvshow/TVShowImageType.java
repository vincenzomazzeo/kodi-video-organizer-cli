package com.ninjatech.kodivideoorganizercli.model.tvshow;

import java.util.Arrays;

public enum TVShowImageType {

    BANNER("banner", "banner.jpg"),
    CHARACTER("character", "character.png"),
    CLEARART("clearart", "clearart.png"),
    FANART("fanart", "fanart.jpg"),
    LANDSCAPE("landscape", "landscape.jpg"),
    LOGO("logo", "logo.png"),
    POSTER("poster", "poster.jpg"),
    SEASON_FOLDER("season-folder", "season%02d-poster.jpg");

    private final String value;
    private final String fileName;

    private TVShowImageType(String value,
                            String fileName) {
        this.value = value;
        this.fileName = fileName;
    }

    public String getValue() {
        return this.value;
    }

    public String getFileName() {
        return this.fileName;
    }

    public static TVShowImageType parse(String value) {
        return Arrays.stream(values())
                     .filter(e -> e.value.equals(value))
                     .findFirst()
                     .orElse(null);
    }

}
