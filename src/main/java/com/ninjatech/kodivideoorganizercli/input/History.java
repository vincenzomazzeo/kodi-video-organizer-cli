package com.ninjatech.kodivideoorganizercli.input;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class History {

    private final List<String> data;
    private Integer currentPointer;

    protected History() {
        this.data = new LinkedList<>();
        this.currentPointer = -1;
    }

    protected void resetPointer() {
        this.currentPointer = -1;
    }

    protected void add(String line) {
        this.data.add(0, line);
        resetPointer();
    }

    protected String getPrevious() {
        return this.currentPointer < this.data.size() - 1 ? this.data.get(++this.currentPointer) : StringUtils.EMPTY;
    }

    protected String getNext() {
        if (this.currentPointer > -1) {
            this.currentPointer--;
        }
        return this.currentPointer >= 0 ? this.data.get(this.currentPointer) : StringUtils.EMPTY;
    }

}
