package com.ninjatech.kodivideoorganizercli.command;

import org.apache.commons.cli.CommandLine;

public class CommandExecutor implements Runnable {

    private final CommandHandler commandHandler;
    private final AbstractCommand command;
    private final CommandLine commandLine;
    private final CommandInputChannel inputChannel;
    private final CommandOutputChannel outputChannel;

    public CommandExecutor(CommandHandler commandHandler,
                           AbstractCommand command,
                           CommandLine commandLine,
                           CommandInputChannel inputChannel,
                           CommandOutputChannel outputChannel) {
        this.commandHandler = commandHandler;
        this.command = command;
        this.commandLine = commandLine;
        this.inputChannel = inputChannel;
        this.outputChannel = outputChannel;
    }

    @Override
    public void run() {
        try {
            this.command.execute(this.commandLine, this.inputChannel, this.outputChannel);
        }
        catch (Exception e) {
            this.outputChannel.appendError(e);
            e.printStackTrace();
        }
        this.commandHandler.notifyCommandCompletition();
    }

}
