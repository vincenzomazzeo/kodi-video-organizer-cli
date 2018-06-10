package com.ninjatech.kodivideoorganizercli.expander;

import java.util.EnumSet;
import java.util.function.Function;
import java.util.stream.Stream;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class EnumCommandExpander<E extends Enum<E>> extends AbstractCommandExpander<E> {

    private final Class<E> enumType;

    protected EnumCommandExpander(String input,
                           OutputComponent outputComponent,
                           Command command,
                           Function<E, String> mapper,
                           Class<E> enumType) {
        super(input, outputComponent, command, mapper);

        this.enumType = enumType;
    }

    @Override
    protected Stream<E> getStream() {
        return EnumSet.allOf(this.enumType)
                      .stream();
    }

}
