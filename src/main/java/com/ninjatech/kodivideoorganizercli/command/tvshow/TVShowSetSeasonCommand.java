package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.io.IOException;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowSeason;
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowSetSeasonCommand extends AbstractTVShowMandatoryCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_SET_SEASON;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Sets a Season for the current TV Show";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TVShowUtils.setSeasons(this.environmentManager, this.environmentManager.getSelectedTVShow());
        List<TVShowSeason> seasons = this.environmentManager.getSelectedTVShow()
                                                            .getSeasons();
        if (seasons.isEmpty()) {
            outputChannel.appendLine("No Season found");
        }
        else {
            Chooser<TVShowSeason> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT),
                                                          inputChannel,
                                                          outputChannel,
                                                          TVShowUtils::getSeasonWithFolderCheck);
            Chooser<TVShowSeason>.ChooserResult result = chooser.execute(seasons, "Season");
            if (result.getType() == ChooserResultType.CHOOSEN) {
                TVShowSeason season = result.getElement();
                if (Files.exists(season.getFolder())) {
                    this.environmentManager.getSelectedTVShow()
                                           .selectSeason(season);
                }
                else {
                    createSeason(season, outputChannel);
                }
            }
        }
    }

    public void createSeason(TVShowSeason season, CommandOutputChannel outputChannel) {
        try {
            Files.createDirectory(season.getFolder());
            this.environmentManager.getSelectedTVShow()
                                   .selectSeason(season);
            outputChannel.appendLine("'%s' created and selected", season.getName());
        }
        catch (IOException e) {
            outputChannel.appendError("Failed to create Season");
            e.printStackTrace();
        }
    }

}
