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
public class SetFanartTVDataCommand extends AbstractCommand {

    private static final String API = "api";

    @Override
    public Command getCommand() {
        return Command.SET_FANARTTV_DATA;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Sets FanartTV Data";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        String api = commandLine.getOptionValue(SetFanartTVDataCommand.API);
        this.environmentManager.setFanartTVData(api);
    }

    @Override
    protected void setOptions(Options options) {
        options.addOption(Option.builder(SetFanartTVDataCommand.API)
                                .desc("API")
                                .hasArg(true)
                                .numberOfArgs(1)
                                .required(true)
                                .type(String.class)
                                .build());
    }

}
