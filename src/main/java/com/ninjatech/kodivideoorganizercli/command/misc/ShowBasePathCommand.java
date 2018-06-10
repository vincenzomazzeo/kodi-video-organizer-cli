package com.ninjatech.kodivideoorganizercli.command.misc;

import java.nio.file.Path;
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
public class ShowBasePathCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.SHOW_BASE_PATH;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Shows the Base Path";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        outputChannel.appendLine(this.environmentManager.getBasePath()
                                                        .toAbsolutePath()
                                                        .toString());
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        Path basePath = this.environmentManager.getBasePath();

        if (basePath == null) {
            outputChannel.appendLine("Base Path not set");
        }

        return basePath != null;
    }

}
