package com.ninjatech.kodivideoorganizercli.expander;

import java.util.function.Function;
import java.util.stream.Stream;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public abstract class AbstractCommandExpander<T> extends AbstractExpander {

    private final Command command;
    private final Function<T, String> mapper;

    protected AbstractCommandExpander(String input,
                                      OutputComponent outputComponent,
                                      Command command,
                                      Function<T, String> mapper) {
        super(input, outputComponent);

        this.command = command;
        this.mapper = mapper;
    }

    protected abstract Stream<T> getStream();

    @Override
    public String expand() {
        String expandedValue = expand(getStream().map(this.mapper));
        return String.format("%s %s", this.command.getName(), expandedValue != null ? expandedValue : this.input);
    }

    @Override
    public void printChoices() {
        printChoices(getStream().map(this.mapper));
        this.outputComponent.appendEmptyLine();
    }

}
