package com.ninjatech.kodivideoorganizercli.output;

import java.awt.Color;

public enum OutputColor {

    BLACK(0, 0, 0),
    RED(128, 0, 0),
    GREEN(0, 128, 0),
    YELLOW(128, 128, 0),
    BLUE(0, 0, 128),
    MAGENTA(128, 0, 128),
    CYAN(0, 128, 128),
    WHITE(192, 192, 192),
    BRIGHT_BLACK(128, 128, 128),
    BRIGHT_RED(255, 0, 0),
    BRIGHT_GREEN(0, 255, 0),
    BRIGHT_YELLOW(255, 255, 0),
    BRIGHT_BLUE(0, 0, 255),
    BRIGHT_MAGENTA(255, 0, 255),
    BRIGHT_CYAN(0, 255, 255),
    BRIGHT_WHITE(255, 255, 255);

    private final Color color;

    private OutputColor(int r,
                         int g,
                         int b) {
        this.color = new Color(r, g, b);
    }

    protected Color getColor() {
        return this.color;
    }

}
