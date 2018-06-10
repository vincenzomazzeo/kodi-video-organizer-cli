package com.ninjatech.kodivideoorganizercli.command.movie;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;

@Component
public class MovieUnsetCollection extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_UNSET_COLLECTION;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Unsets the Movie Collection";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        this.environmentManager.resetMovieCollection();
    }

}
