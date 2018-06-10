package com.ninjatech.kodivideoorganizercli.command.tvshow;

import org.apache.commons.cli.CommandLine;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;

public abstract class AbstractTVShowMandatoryCommand extends AbstractCommand {

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        TVShow tvShow = this.environmentManager.getSelectedTVShow();

        if (tvShow == null) {
            outputChannel.appendLine("No TV Show selected");
        }

        return tvShow != null;
    }

}
