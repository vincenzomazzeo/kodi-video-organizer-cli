package com.ninjatech.kodivideoorganizercli.command.misc;

import java.io.File;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;

@Component
public class SetBasePathCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.SET_BASE_PATH;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Sets the Base Path";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        File basePath = new File(joinArgs(commandLine));
        if (basePath.exists()) {
            this.environmentManager.setBasePath(basePath.toPath());
            outputChannel.appendLine("Base Path set to %s", basePath.getAbsolutePath());
            this.environmentManager.resetTVShow();
        }
        else {
            outputChannel.appendLine("Path not found");
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length > 0;
    }

}
