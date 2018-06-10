package com.ninjatech.kodivideoorganizercli.command.misc;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.ErrorLog;
import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;

@Component
public class FlushErrorLogCommand extends AbstractCommand {

    private final ErrorLog errorLog;

    public FlushErrorLogCommand(@Autowired ErrorLog errorLog) {
        this.errorLog = errorLog;
    }

    @Override
    public Command getCommand() {
        return Command.FLUSH_ERROR_LOG;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Flushes the Error Log to Console";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        outputChannel.appendError(this.errorLog.getAndResetBuffer());
    }

}
