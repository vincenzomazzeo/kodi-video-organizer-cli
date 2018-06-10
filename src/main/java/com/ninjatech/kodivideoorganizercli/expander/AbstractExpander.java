package com.ninjatech.kodivideoorganizercli.expander;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public abstract class AbstractExpander {

    protected final String input;
    protected final OutputComponent outputComponent;

    protected AbstractExpander(String input,
                               OutputComponent outputComponent) {
        this.input = input;
        this.outputComponent = outputComponent;
    }

    protected abstract String expand();

    protected abstract void printChoices();

    protected String expand(Stream<String> values) {
        String result = null;

        String[] candidates = values.filter(e -> StringUtils.startsWithIgnoreCase(e, this.input)
                                                 && !e.equalsIgnoreCase(this.input))
                                    .toArray(String[]::new);
        if (candidates.length == 1) {
            result = candidates[0];
        }
        else if (candidates.length > 1) {
            result = StringUtils.getCommonPrefix(candidates);
        }

        return result;
    }

    protected void printChoices(Stream<String> values) {
        values.filter(e -> StringUtils.startsWithIgnoreCase(e, this.input)
                           && !e.equalsIgnoreCase(this.input))
              .sorted((a, b) -> a.compareTo(b))
              .forEach(e -> this.outputComponent.appendLine(e));
    }

}
