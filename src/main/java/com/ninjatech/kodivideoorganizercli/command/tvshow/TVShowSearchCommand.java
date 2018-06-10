package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowSearchCommand extends AbstractTVShowLanguageCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_SEARCH;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Searches for a TV Show";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TVShow tvShow = TVShowUtils.search(this.environmentManager,
                                           inputChannel,
                                           outputChannel,
                                           joinArgs(commandLine),
                                           getLanguage(commandLine));
        if (tvShow != null) {
            handleTVShow(tvShow, outputChannel);
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length > 0;
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        if (!this.environmentManager.getTheTVDBComConnector()
                                    .isActive()) {
            outputChannel.appendLine("TheTVDB.com is not active");
            return false;
        }
        if (this.environmentManager.getBasePath() == null) {
            outputChannel.appendLine("Base Path not set");
            return false;
        }

        return true;
    }

    private void handleTVShow(TVShow tvShow, CommandOutputChannel outputChannel) {
        Path folder = this.environmentManager.getBasePath()
                                             .resolve(tvShow.getName());
        if (Files.exists(folder)) {
            outputChannel.appendLine("TV Show already present");
        }

        try {
            folder = Files.createDirectory(folder);
            tvShow.setFolder(folder);
            this.environmentManager.selectTVShow(tvShow);
            outputChannel.appendLine("'%s' created and selected", tvShow.getName());
        }
        catch (IOException e) {
            outputChannel.appendError("Failed to create TV Show");
            e.printStackTrace();
        }
    }

}
