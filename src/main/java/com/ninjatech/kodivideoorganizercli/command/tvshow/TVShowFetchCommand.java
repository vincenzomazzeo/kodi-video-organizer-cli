package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowFetchCommand extends AbstractTVShowLanguageCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_FETCH;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Binds a folder to the TV Show fetching data";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Path basePath = this.environmentManager.getBasePath();
        String prefix = commandLine.getArgs().length > 0 ? joinArgs(commandLine) : StringUtils.EMPTY;
        List<Path> folders = Files.list(basePath)
                                  .filter(e -> Files.isDirectory(e)
                                               && StringUtils.startsWithIgnoreCase(e.getFileName()
                                                                                    .toString(),
                                                                                   prefix))
                                  .collect(Collectors.toList());
        if (folders.isEmpty()) {
            outputChannel.appendLine("No Folder found");
        }
        else {
            handleFolders(folders, commandLine, inputChannel, outputChannel);
        }
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

    private void handleFolders(List<Path> folders,
                               CommandLine commandLine,
                               CommandInputChannel inputChannel,
                               CommandOutputChannel outputChannel) throws Exception {
        Chooser<Path> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT),
                                              inputChannel,
                                              outputChannel,
                                              e -> e.getFileName()
                                                    .toString());
        Chooser<Path>.ChooserResult result = chooser.execute(folders, "Folder");
        if (result.getType() == ChooserResultType.CHOOSEN) {
            Path folder = result.getElement();
            if (folder != null) {
                TVShow tvShow = TVShowUtils.search(this.environmentManager,
                                                   inputChannel,
                                                   outputChannel,
                                                   folder.getFileName()
                                                         .toString(),
                                                   getLanguage(commandLine));
                if (tvShow != null) {
                    tvShow.setFolder(folder);
                    this.environmentManager.selectTVShow(tvShow);
                }
            }
        }
    }

}
