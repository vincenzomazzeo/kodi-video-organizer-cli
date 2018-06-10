package com.ninjatech.kodivideoorganizercli.expander;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class ExpanderHandler {

    private static final AbstractExpander VOID_EXPANDER = new VoidExpander(null, null);

    private final OutputComponent outputComponent;
    private final CommandRepository commandRepository;

    public ExpanderHandler(OutputComponent outputComponent,
                           CommandRepository commandRepository) {
        this.outputComponent = outputComponent;
        this.commandRepository = commandRepository;
    }

    public String expand(String input) {
        return getExpander(input, false).expand();
    }

    public void printChoices(String input) {
        getExpander(input, true).printChoices();
    }

    private AbstractExpander getExpander(String input, boolean choices) {
        AbstractExpander result = ExpanderHandler.VOID_EXPANDER;

        String[] splittedInput = splitInput(input);
        if (splittedInput != null) {
            if (splittedInput.length > 1 || splittedInput.length == 1 && choices) {
                Command command = Command.parse(this.commandRepository.getAvailableCommands(), splittedInput[0]);
                result = buildExpander(command, getInputParameter(splittedInput));
            }
            else {
                result = new CommandExpander(input, this.outputComponent, this.commandRepository);
            }
        }

        return result;
    }

    private String[] splitInput(String input) {
        return StringUtils.isNotBlank(input) ? input.split(" ") : null;
    }

    private String getInputParameter(String[] splittedInput) {
        return Arrays.stream(Arrays.copyOfRange(splittedInput,
                                                1,
                                                splittedInput.length))
                     .collect(Collectors.joining(" "));
    }

    private AbstractExpander buildExpander(Command command, String input) {
        switch (command) {
        case HELP:
            return new HelpCommandExpander(input, this.outputComponent, this.commandRepository);
        case SET_BASE_PATH:
            return new DirectoryCommandExpander(input, this.outputComponent, command, File::getAbsolutePath);
        case SET_MODE:
            return new EnumCommandExpander<>(input,
                                             this.outputComponent,
                                             command,
                                             CommandRepository.Type::getDesc,
                                             CommandRepository.Type.class);
        case TV_SHOW_SET_IMAGE:
        case TV_SHOW_SHOW_IMAGE:
            return new EnumCommandExpander<>(input,
                                             this.outputComponent,
                                             command,
                                             TVShowImageType::getValue,
                                             TVShowImageType.class);
        default:
            return ExpanderHandler.VOID_EXPANDER;
        }
    }

}
