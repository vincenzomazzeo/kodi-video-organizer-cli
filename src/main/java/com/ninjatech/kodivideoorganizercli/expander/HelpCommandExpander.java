package com.ninjatech.kodivideoorganizercli.expander;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class HelpCommandExpander extends CommandExpander {

    protected HelpCommandExpander(String input,
                                  OutputComponent outputComponent,
                                  CommandRepository commandRepository) {
        super(input, outputComponent, commandRepository);
    }

    @Override
    protected String expand() {
        String expandedValue = super.expand();
        return String.format("%s %s", Command.HELP.getName(), expandedValue != null ? expandedValue : this.input);
    }

}
