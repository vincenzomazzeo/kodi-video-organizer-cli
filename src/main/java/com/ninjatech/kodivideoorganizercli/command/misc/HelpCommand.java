package com.ninjatech.kodivideoorganizercli.command.misc;

import java.util.Comparator;
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
public class HelpCommand extends AbstractCommand {

    @Autowired
    private CommandRepository commandRepository;

    @Override
    public Command getCommand() {
        return Command.HELP;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Prints this help";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        if (commandLine.getArgs().length == 0) {
            Set<Command> commands = this.commandRepository.getAvailableCommands();
            int length = commands.stream()
                                 .max(new Comparator<Command>() {

                                     @Override
                                     public int compare(Command c1, Command c2) {
                                         return c1.getName()
                                                  .length()
                                                - c2.getName()
                                                    .length();
                                     }

                                 })
                                 .get()
                                 .getName()
                                 .length();
            String format = "%-".concat(String.valueOf(length))
                                .concat("s     %s");
            commands.stream()
                    .forEach(c -> {
                        outputChannel.appendLine(format,
                                                 c.getName(),
                                                 this.commandRepository.getCommand(c)
                                                                       .getShortDescription());
                    });
        }
        else {
            AbstractCommand command = this.commandRepository.getCommand(Command.parse(this.commandRepository.getAvailableCommands(),
                                                                                      commandLine.getArgs()[0]));
            if (command == null) {
                outputChannel.appendLine("Command not found in the current mode");
            }
            else {
                command.printHelp(outputChannel);
            }
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length == 0 || commandLine.getArgs().length == 1;
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        boolean result = true;

        if (commandLine.getArgs().length == 1) {
            Command command = Command.parse(this.commandRepository.getAvailableCommands(), commandLine.getArgs()[0]);
            if (command == null) {
                result = false;
                outputChannel.appendLine("Command not found");
            }
        }

        return result;
    }

}
