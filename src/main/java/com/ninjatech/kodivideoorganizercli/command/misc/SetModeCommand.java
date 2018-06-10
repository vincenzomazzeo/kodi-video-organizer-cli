package com.ninjatech.kodivideoorganizercli.command.misc;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;

@Component
public class SetModeCommand extends AbstractCommand {

    @Autowired
    private CommandRepository commandRepository;

    @Override
    public Command getCommand() {
        return Command.SET_MODE;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MAIN, Type.TV_SHOW, Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Changes the Prompt Mode";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Type type = null;
        if (commandLine.getArgs().length == 0) {
            type = Type.MAIN;
        }
        else {
            type = CommandRepository.Type.parse(commandLine.getArgs()[0]);
        }

        if (type == null) {
            outputChannel.appendLine("Invalid mode");
        }
        else if (type == this.commandRepository.getActiveType()) {
            outputChannel.appendLine("Already in %s mode", type.getDesc());
        }
        else {
            this.environmentManager.resetTVShow();
            this.environmentManager.resetMovieCollection();
            this.commandRepository.setActiveType(type);
            outputChannel.appendLine("Switched to %s mode", type.getDesc());
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length <= 1;
    }

}
