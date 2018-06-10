package com.ninjatech.kodivideoorganizercli.command.connector;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;

@Component
public class SetTheTVDBComDataCommand extends AbstractCommand {

    private static final String API = "api";
    private static final String LANG = "lang";

    @Override
    public Command getCommand() {
        return Command.SET_THETVDBCOM_DATA;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Sets TheTVDB.com Data";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        String api = commandLine.getOptionValue(SetTheTVDBComDataCommand.API);
        String language = commandLine.getOptionValue(SetTheTVDBComDataCommand.LANG);
        this.environmentManager.setTheTVDBComData(api, language);
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length == 0 &&
               commandLine.getOptionValue(SetTheTVDBComDataCommand.LANG)
                          .length() == 2;
    }

    @Override
    protected void setOptions(Options options) {
        options.addOption(Option.builder(SetTheTVDBComDataCommand.API)
                                .desc("API")
                                .hasArg(true)
                                .numberOfArgs(1)
                                .required(true)
                                .type(String.class)
                                .build());
        options.addOption(Option.builder(SetTheTVDBComDataCommand.LANG)
                                .desc("Preferred Language")
                                .hasArg(true)
                                .numberOfArgs(1)
                                .required(true)
                                .type(String.class)
                                .build());
    }

}
