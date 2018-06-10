package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.Comparators;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowEpisode;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowSeason;
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowBindEpisodesCommand extends AbstractTVShowLanguageCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_BIND_EPISODES;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Binds Episodes to Files";
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
            Map<Path, TVShowEpisode> data = new LinkedHashMap<>();
            for (Path unboundFile : unboundFiles) {
                Chooser<TVShowEpisode>.ChooserResult chooserResult = bind(unboundFile,
                                                                          unboundEpisodes,
                                                                          inputChannel,
                                                                          outputChannel);
                if (chooserResult.getType() == ChooserResultType.CHOOSEN) {
                    TVShowEpisode episode = chooserResult.getElement();
                    if (episode != null) {
                        unboundEpisodes.remove(episode);
                        data.put(unboundFile, episode);
                    }
                }
                else if (chooserResult.getType() == ChooserResultType.ABORTED) {
                    return;
                }
            }
            if (!data.isEmpty()) {
                printSummary(data, outputChannel);
                if (askForOption("Confirm", inputChannel, outputChannel)) {
                    executeBind(data);
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

    private Chooser<TVShowEpisode>.ChooserResult bind(Path file,
                                                      List<TVShowEpisode> episodes,
                                                      CommandInputChannel inputChannel,
                                                      CommandOutputChannel outputChannel) throws Exception {
        Chooser<TVShowEpisode> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT,
                                                                  Chooser.ChooserType.SKIP),
                                                       inputChannel,
                                                       outputChannel,
                                                       TVShowUtils::getEpisodeName);
        return chooser.execute(episodes,
                               String.format("Episode for '%s'",
                                             file.getFileName()
                                                 .toString()));
    }

    private void printSummary(Map<Path, TVShowEpisode> data, CommandOutputChannel outputChannel) {
        outputChannel.appendLine("");

        int length = data.keySet()
                         .stream()
                         .map(e -> e.getFileName()
                                    .toString()
                                    .length())
                         .max(Comparators.comparable())
                         .orElse(0);
        String format = " %-".concat(String.valueOf(length + 1))
                             .concat("s -> %s");
        data.entrySet()
            .stream()
            .forEach(e -> outputChannel.appendLine(format,
                                                   e.getKey()
                                                    .getFileName()
                                                    .toString(),
                                                   TVShowUtils.getEpisodeName(e.getValue())));
    }

    private void executeBind(Map<Path, TVShowEpisode> data) throws IOException {
        for (Entry<Path, TVShowEpisode> entry : data.entrySet()) {
            String extension = FilenameUtils.getExtension(entry.getKey()
                                                               .getFileName()
                                                               .toString());
            Path file = Files.move(entry.getKey(),
                                   entry.getKey()
                                        .resolveSibling(String.format("%s.%s",
                                                                      TVShowUtils.getFullEpisodeName(entry.getValue()),
                                                                      extension)));
            entry.getValue()
                 .setFile(file);
        }
    }

}
