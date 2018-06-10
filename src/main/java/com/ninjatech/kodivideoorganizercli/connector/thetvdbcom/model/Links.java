package com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {

    private final Integer first;
    private final Integer last;
    private final Integer next;
    private final Integer previous;

    @JsonCreator
    public Links(@JsonProperty("first") Integer first,
                 @JsonProperty("last") Integer last,
                 @JsonProperty("next") Integer next,
                 @JsonProperty("previous") Integer previous) {
        this.first = first;
        this.last = last;
        this.next = next;
        this.previous = previous;
    }

    public Integer getFirst() {
        return this.first;
    }

    public Integer getLast() {
        return this.last;
    }

    public Integer getNext() {
        return this.next;
    }

    public Integer getPrevious() {
        return this.previous;
    }

}
