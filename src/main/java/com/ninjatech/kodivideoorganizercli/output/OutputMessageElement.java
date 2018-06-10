package com.ninjatech.kodivideoorganizercli.output;

import java.awt.Color;

public class OutputMessageElement {

    private final String text;
    private final Color color;
    private final Boolean bold;

    protected OutputMessageElement(String text,
                                   Color color,
                                   Boolean bold) {
        this.text = text;
        this.color = color;
        this.bold = bold;
    }

    public String getText() {
        return this.text;
    }

    public Color getColor() {
        return this.color;
    }

    public Boolean getBold() {
        return this.bold;
    }

}
