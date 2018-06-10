package com.ninjatech.kodivideoorganizercli.command.proxy;

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
public class SetProxyCommand extends AbstractCommand {

    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public Command getCommand() {
        return Command.SET_PROXY;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MAIN);
    }

    @Override
    public String getShortDescription() {
        return "Sets Proxy";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        String host = commandLine.getOptionValue(SetProxyCommand.HOST);
        Integer port = Integer.valueOf(commandLine.getOptionValue(SetProxyCommand.PORT, "80"));
        String username = commandLine.getOptionValue(SetProxyCommand.USERNAME);
        String password = commandLine.getOptionValue(SetProxyCommand.PASSWORD);
        this.environmentManager.setProxy(host, port, username, password);
    }

    @Override
    protected void setOptions(Options options) {
        options.addOption(Option.builder(SetProxyCommand.HOST)
                                .required(true)
                                .hasArg(true)
                                .numberOfArgs(1)
                                .type(String.class)
                                .desc("Proxy Host")
                                .build());
        options.addOption(Option.builder(SetProxyCommand.PORT)
                                .required(false)
                                .hasArg(true)
                                .numberOfArgs(1)
                                .type(Integer.class)
                                .desc("Proxy Port")
                                .build());
        options.addOption(Option.builder(SetProxyCommand.USERNAME)
                                .required(false)
                                .hasArg(true)
                                .numberOfArgs(1)
                                .type(String.class)
                                .desc("Proxy Username")
                                .build());
        options.addOption(Option.builder(SetProxyCommand.PASSWORD)
                                .required(false)
                                .hasArg(true)
                                .numberOfArgs(1)
                                .type(String.class)
                                .desc("Proxy Password")
                                .build());
    }

}
