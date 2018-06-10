package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.util.TVShowUtils;

@Component
public class TVShowShowSeasonsCommand extends AbstractTVShowMandatoryCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_SHOW_SEASONS;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Shows the current TV Show Seasons";
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
        this.environmentManager.getSelectedTVShow()
                               .getSeasons()
                               .forEach(e -> outputChannel.appendLine(TVShowUtils.getSeasonWithFolderCheck(e)));
    }

}
