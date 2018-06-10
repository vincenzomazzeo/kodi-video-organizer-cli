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
import com.ninjatech.kodivideoorganizercli.settings.model.TheMovieDB;

@Component
public class ShowTheMovieDBDataCommand extends AbstractCommand {

    @Autowired
    private SettingsHandler settingsHandler;

    @Override
    public Command getCommand() {
        return Command.SHOW_THEMOVIEDB_DATA;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.allOf(Type.class);
    }

    @Override
    public String getShortDescription() {
        return "Shows TheMovieDB Data";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TheMovieDB theMovieDB = this.settingsHandler.getTheMovieDB();
        outputChannel.appendLine("API: %s", theMovieDB.getApi());
        outputChannel.appendLine("Preferred Language: %s", theMovieDB.getLanguage());
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        TheMovieDB theMovieDB = this.settingsHandler.getTheMovieDB();

        if (theMovieDB == null) {
            outputChannel.appendLine("TheMovieDB data is not set");
        }

        return theMovieDB != null;
    }

}
