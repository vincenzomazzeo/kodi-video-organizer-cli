package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.Comparators;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowEpisode;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowShowEpisodesCommand extends AbstractTVShowLanguageCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_SHOW_EPISODES;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Shows the episodes of the selected Season";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TVShowUtils.setEpisodes(this.environmentManager,
                                this.environmentManager.getSelectedTVShow()
                                                       .getSelectedSeason(),
                                getLanguage(commandLine));
        List<TVShowEpisode> episodes = this.environmentManager.getSelectedTVShow()
                                                              .getSelectedSeason()
                                                              .getEpisodes();
        Integer length = episodes.stream()
                                 .map(e -> e.getName()
                                            .length())
                                 .max(Comparators.comparable())
                                 .orElse(0);
        episodes.forEach(e -> outputChannel.appendLine(TVShowUtils.getEpisodeNameWithFileCheck(e, length)));
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

}
