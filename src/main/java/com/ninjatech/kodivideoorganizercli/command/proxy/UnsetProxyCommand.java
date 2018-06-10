package com.ninjatech.kodivideoorganizercli.command.proxy;

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
public class UnsetProxyCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.UNSET_PROXY;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MAIN);
    }

    @Override
    public String getShortDescription() {
        return "Unsets Proxy";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        this.environmentManager.unsetProxy();
    }

}
