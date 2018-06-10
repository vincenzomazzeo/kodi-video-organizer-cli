package com.ninjatech.kodivideoorganizercli.output;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class OutputMessageBuilder {

    private static final Color DEFAULT_COLOR = OutputColor.WHITE.getColor();

    public static OutputMessageBuilder outputMessageBuilder() {
        return new OutputMessageBuilder();
    }

    private final List<OutputMessageElement> elements;
    private Color color;
    private Boolean bold;

    private OutputMessageBuilder() {
        this.elements = new LinkedList<>();
        this.color = OutputMessageBuilder.DEFAULT_COLOR;
        this.bold = false;
    }

    public OutputMessageBuilder add(OutputMessageBuilder builder) {
        this.elements.addAll(builder.elements);
        return this;
    }

    public OutputMessageBuilder text(String text) {
        this.elements.add(new OutputMessageElement(text, this.color, this.bold));
        return this;
    }

    public OutputMessageBuilder textLine(String text) {
        this.elements.add(new OutputMessageElement(text.concat("\n"), this.color, this.bold));
        return this;
    }

    public OutputMessageBuilder reset() {
        this.color = OutputMessageBuilder.DEFAULT_COLOR;
        this.bold = false;
        return this;
    }

    public OutputMessageBuilder black() {
        this.color = OutputColor.BLACK.getColor();
        return this;
    }

    public OutputMessageBuilder red() {
        this.color = OutputColor.RED.getColor();
        return this;
    }

    public OutputMessageBuilder green() {
        this.color = OutputColor.GREEN.getColor();
        return this;
    }

    public OutputMessageBuilder yellow() {
        this.color = OutputColor.YELLOW.getColor();
        return this;
    }

    public OutputMessageBuilder blue() {
        this.color = OutputColor.BLUE.getColor();
        return this;
    }

    public OutputMessageBuilder magenta() {
        this.color = OutputColor.MAGENTA.getColor();
        return this;
    }

    public OutputMessageBuilder cyan() {
        this.color = OutputColor.CYAN.getColor();
        return this;
    }

    public OutputMessageBuilder white() {
        this.color = OutputColor.WHITE.getColor();
        return this;
    }

    public OutputMessageBuilder brightBlack() {
        this.color = OutputColor.BRIGHT_BLACK.getColor();
        return this;
    }

    public OutputMessageBuilder brightRed() {
        this.color = OutputColor.BRIGHT_RED.getColor();
        return this;
    }

    public OutputMessageBuilder brightGreen() {
        this.color = OutputColor.BRIGHT_GREEN.getColor();
        return this;
    }

    public OutputMessageBuilder brightYellow() {
        this.color = OutputColor.BRIGHT_YELLOW.getColor();
        return this;
    }

    public OutputMessageBuilder brightBlue() {
        this.color = OutputColor.BRIGHT_BLUE.getColor();
        return this;
    }

    public OutputMessageBuilder brightMagenta() {
        this.color = OutputColor.BRIGHT_MAGENTA.getColor();
        return this;
    }

    public OutputMessageBuilder brightCyan() {
        this.color = OutputColor.BRIGHT_CYAN.getColor();
        return this;
    }

    public OutputMessageBuilder brightWhite() {
        this.color = OutputColor.BRIGHT_WHITE.getColor();
        return this;
    }

    public OutputMessageBuilder bold() {
        this.bold = true;
        return this;
    }

    public OutputMessageBuilder plain() {
        this.bold = false;
        return this;
    }

    public OutputMessageBuilder black(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BLACK.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder red(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.RED.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder green(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.GREEN.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder yellow(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.YELLOW.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder blue(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BLUE.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder magenta(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.MAGENTA.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder cyan(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.CYAN.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder white(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.WHITE.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightBlack(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_BLACK.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightRed(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_RED.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightGreen(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_GREEN.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightYellow(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_YELLOW.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightBlue(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_BLUE.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightMagenta(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_MAGENTA.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightCyan(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_CYAN.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder brightWhite(String text) {
        this.elements.add(new OutputMessageElement(text, OutputColor.BRIGHT_WHITE.getColor(), this.bold));
        return this;
    }

    public OutputMessageBuilder plain(String text) {
        this.elements.add(new OutputMessageElement(text, this.color, false));
        return this;
    }

    public OutputMessageBuilder bold(String text) {
        this.elements.add(new OutputMessageElement(text, this.color, true));
        return this;
    }

    public List<OutputMessageElement> build() {
        return this.elements;
    }

}
