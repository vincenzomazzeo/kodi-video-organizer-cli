package com.ninjatech.kodivideoorganizercli.expander;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class CommandExpander extends AbstractExpander {

    private final CommandRepository commandRepository;

    protected CommandExpander(String input,
                              OutputComponent outputComponent,
                              CommandRepository commandRepository) {
        super(input, outputComponent);

        this.commandRepository = commandRepository;
    }

    @Override
    protected String expand() {
        return expand(this.commandRepository.getAvailableCommands()
                                            .stream()
                                            .map(Command::getName));
    }

    @Override
    protected void printChoices() {
        printChoices(this.commandRepository.getAvailableCommands()
                                           .stream()
                                           .map(Command::getName));
    }

}
