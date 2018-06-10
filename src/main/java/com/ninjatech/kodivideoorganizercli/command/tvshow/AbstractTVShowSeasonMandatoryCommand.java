package com.ninjatech.kodivideoorganizercli.command.tvshow;

import org.apache.commons.cli.CommandLine;

import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;

public abstract class AbstractTVShowSeasonMandatoryCommand extends AbstractTVShowMandatoryCommand {

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
