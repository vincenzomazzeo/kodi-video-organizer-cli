package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowEpisode;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowSeason;
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserType;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowBindMultiEpisodeCommand extends AbstractTVShowLanguageCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_BIND_MULTI_EPISODE;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Binds Multi-Episode to File";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TVShowSeason season = this.environmentManager.getSelectedTVShow()
                                                     .getSelectedSeason();
        TVShowUtils.setEpisodes(this.environmentManager, season, getLanguage(commandLine));

        List<Path> unboundFiles = TVShowUtils.getUnboundFiles(season);
        List<TVShowEpisode> unboundEpisodes = TVShowUtils.getUnboundEpisodes(season);
        if (unboundFiles.isEmpty()) {
            outputChannel.appendLine("No Video File to bind");
        }
        else if (unboundEpisodes.isEmpty()) {
            outputChannel.appendLine("No Episode to bind");
        }
        else {
            Chooser<Path>.ChooserResult pathChooserResult = choosePath(unboundFiles, inputChannel, outputChannel);
            if (pathChooserResult.getType() == ChooserResultType.CHOOSEN) {
                Path path = pathChooserResult.getElement();
                List<TVShowEpisode> episodes = new LinkedList<>();
                boolean chooseAgain = false;
                do {
                    Chooser<TVShowEpisode>.ChooserResult episodeChooserResult = chooseEpisode(unboundEpisodes,
                                                                                              inputChannel,
                                                                                              outputChannel);
                    if (episodeChooserResult.getType() == ChooserResultType.CHOOSEN) {
                        TVShowEpisode episode = episodeChooserResult.getElement();
                        episodes.add(episode);
                        unboundEpisodes.remove(episode);
                        chooseAgain = askForOption("Choose Again", inputChannel, outputChannel);
                    }
                    else {
                        return;
                    }
                }
                while (chooseAgain);

                if (episodes.size() > 1) {
                    printSummary(path, episodes, outputChannel);
                    if (askForOption("Confirm", inputChannel, outputChannel)) {
                        executeBind(path, episodes);
                    }
                }
                else {
                    outputChannel.appendLine("Chosen less than 2 Episodes. Aborted.");
                }
            }
        }
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        boolean result = true;

        result = super.checkConstraints(commandLine, outputChannel);
        if (result) {
            TVShow tvShow = this.environmentManager.getSelectedTVShow();
            if (tvShow.getSelectedSeason() == null) {
                outputChannel.appendLine("No Season selected");
            }
        }

        return result;
    }

    private Chooser<Path>.ChooserResult choosePath(List<Path> files,
                                                   CommandInputChannel inputChannel,
                                                   CommandOutputChannel outputChannel) throws Exception {
        Chooser<Path> chooser = new Chooser<>(EnumSet.of(ChooserType.ABORT),
                                              inputChannel,
                                              outputChannel,
                                              e -> e.getFileName()
                                                    .toString());
        return chooser.execute(files, "File");
    }

    private Chooser<TVShowEpisode>.ChooserResult chooseEpisode(List<TVShowEpisode> episodes,
                                                               CommandInputChannel inputChannel,
                                                               CommandOutputChannel outputChannel) throws Exception {
        Chooser<TVShowEpisode> chooser = new Chooser<>(EnumSet.of(ChooserType.ABORT),
                                                       inputChannel,
                                                       outputChannel,
                                                       TVShowUtils::getEpisodeName);
        return chooser.execute(episodes, "Episode");
    }

    private void printSummary(Path path, List<TVShowEpisode> episodes, CommandOutputChannel outputChannel) {
        outputChannel.appendLine("");
        outputChannel.appendLine("The following Episodes will be bound to '%s':",
                                 path.getFileName()
                                     .toString());
        episodes.forEach(e -> outputChannel.appendLine("  %s", TVShowUtils.getEpisodeName(e)));
    }

    private void executeBind(Path path, List<TVShowEpisode> episodes) throws IOException {
        String extension = FilenameUtils.getExtension(path.getFileName()
                                                          .toString());
        String fileName = TVShowUtils.getFullMultiEpisodeName(episodes);
        Path file = Files.move(path,
                               path.resolveSibling(String.format("%s.%s",
                                                                 fileName,
                                                                 extension)));
        episodes.forEach(e -> e.setFile(file));
    }

}
