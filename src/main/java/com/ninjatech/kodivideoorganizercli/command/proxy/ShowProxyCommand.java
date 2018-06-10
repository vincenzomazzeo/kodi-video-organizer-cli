package com.ninjatech.kodivideoorganizercli.command.proxy;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.settings.SettingsHandler;
import com.ninjatech.kodivideoorganizercli.settings.model.Proxy;

@Component
public class ShowProxyCommand extends AbstractCommand {

    @Autowired
    private SettingsHandler settingsHandler;

    @Override
    public Command getCommand() {
        return Command.SHOW_PROXY;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MAIN);
    }

    @Override
    public String getShortDescription() {
        return "Shows Proxy";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Proxy proxy = this.settingsHandler.getProxy();
        outputChannel.appendLine("Proxy: %s@%s:%d",
                                 StringUtils.isNotBlank(proxy.getUsername()) ? proxy.getUsername() : "*",
                                 proxy.getHost(),
                                 proxy.getPort());
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        Proxy proxy = this.settingsHandler.getProxy();
        if (proxy == null) {
            outputChannel.appendLine("Proxy not set");
        }
        return proxy != null;
    }

}
