package com.ninjatech.kodivideoorganizercli.command.connector;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.settings.SettingsHandler;
import com.ninjatech.kodivideoorganizercli.settings.model.FanartTV;

@Component
public class ShowFanartTVDataCommand extends AbstractCommand {

    @Autowired
    private SettingsHandler settingsHandler;

    @Override
    public Command getCommand() {
        return Command.SHOW_FANARTTV_DATA;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Shows FanartTV Data";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        FanartTV fanartTV = this.settingsHandler.getFanartTV();
        outputChannel.appendLine("API: %s", fanartTV.getApi());
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        FanartTV fanartTV = this.settingsHandler.getFanartTV();

        if (fanartTV == null) {
            outputChannel.appendLine("FanartTV data is not set");
        }

        return fanartTV != null;
    }

}
