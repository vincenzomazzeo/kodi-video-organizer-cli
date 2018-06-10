package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;

@Component
public class TVShowCurrentCommand extends AbstractTVShowMandatoryCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_CURRENT;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Shows the selected TV Show if any";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TVShow tvShow = this.environmentManager.getSelectedTVShow();
        outputChannel.appendLine(String.format("%s (%s)",
                                               tvShow.getName(),
                                               tvShow.getFolder()
                                                     .toAbsolutePath()
                                                     .toString()));
    }

}
