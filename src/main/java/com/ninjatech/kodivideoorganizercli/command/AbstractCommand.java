package com.ninjatech.kodivideoorganizercli.command;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;

import com.ninjatech.kodivideoorganizercli.EnvironmentManager;

public abstract class AbstractCommand {

    protected static String joinArgs(CommandLine commandLine) {
        return commandLine.getArgList()
                          .stream()
                          .collect(Collectors.joining(" "));
    }

    @Autowired
    protected EnvironmentManager environmentManager;
    @Autowired
    private HelpFormatter helpFormatter;
    private final Options options;

    protected AbstractCommand() {
        this.options = new Options();

        setOptions(this.options);
    }

    public abstract Command getCommand();

    public abstract Set<CommandRepository.Type> getTypes();

    public abstract String getShortDescription();

    public abstract void printFullDescription(CommandOutputChannel outputChannel);

    public abstract void execute(CommandLine commandLine,
                                 CommandInputChannel inputChannel,
                                 CommandOutputChannel outputChannel) throws Exception;

    public Options getOptions() {
        return this.options;
    }

    public void printHelp(CommandOutputChannel outputChannel) {
        // TODO to refactor after output design
        printFullDescription(outputChannel);
        this.helpFormatter.printHelp(getCommand().getName(), this.options);
        // new HelpFormatter().printHelp("commandName [OPTIONS] <FILE>", Options);
    }

    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length == 0;
    }

    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return true;
    }

    protected void setOptions(Options options) {}

    protected boolean askForOption(String question,
                                   CommandInputChannel inputChannel,
                                   CommandOutputChannel outputChannel) throws Exception {
        String confirm = null;
        do {
            outputChannel.appendLine(String.format("%s (yes/no)?", question));
            confirm = inputChannel.take();
        }
        while (!confirm.equals("yes") && !confirm.equals("no"));

        return confirm.equals("yes");
    }

}
