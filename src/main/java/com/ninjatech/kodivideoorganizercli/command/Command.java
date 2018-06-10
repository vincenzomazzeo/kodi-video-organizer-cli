package com.ninjatech.kodivideoorganizercli.command;

import java.util.Set;

public enum Command {

    // System
    EXIT("exit"),
    FLUSH_ERROR_LOG("flush-error-log"),
    HELP("help"),
    SET_BASE_PATH("set-base-path"),
    SET_PROXY("set-proxy"),
    SET_MODE("set-mode"),
    SHOW_BASE_PATH("show-base-path"),
    SHOW_PROXY("show-proxy"),
    UNSET_PROXY("unset-proxy"),
    // Connector
    SET_FANARTTV_DATA("set-fanart-data"),
    SET_THEMOVIEDB_DATA("set-themoviedb-data"),
    SET_THETVDBCOM_DATA("set-tvdb-data"),
    SHOW_FANARTTV_DATA("show-fanart-data"),
    SHOW_THEMOVIEDB_DATA("show-themoviedb-data"),
    SHOW_THETVDBCOM_DATA("show-tvdb-data"),
    // Movie
    MOVIE_ADD_TO_COLLECTION("add-to-collection"),
    MOVIE_BIND_FILES("bind-files"),
    MOVIE_CREATE_COLLECTION("create-collection"),
    MOVIE_FETCH("fetch"),
    MOVIE_SEARCH("search"),
    MOVIE_SET_COLLECTION("set-collection"),
    MOVIE_UNSET_COLLECTION("unset-collection"),
    // TV Show
    TV_SHOW_BIND_EPISODES("bind-episodes"),
    TV_SHOW_BIND_MULTI_EPISODE("bind-multi-episode"),
    TV_SHOW_CURRENT("current"),
    TV_SHOW_FETCH("fetch"),
    TV_SHOW_SEARCH("search"),
    TV_SHOW_SET_IMAGE("set-image"),
    TV_SHOW_SET_SEASON("set-season"),
    TV_SHOW_SHOW_EPISODES("show-episodes"),
    TV_SHOW_SHOW_IMAGE("show-image"),
    TV_SHOW_SHOW_SEASONS("show-seasons"),
    TV_SHOW_UNSET_SEASON("unset-season"),
    ;

    private final String name;

    private Command(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Command parse(Set<Command> commands, String name) {
        return commands.stream()
                       .filter(c -> c.getName()
                                     .equals(name))
                       .findFirst()
                       .orElse(null);
    }

}
