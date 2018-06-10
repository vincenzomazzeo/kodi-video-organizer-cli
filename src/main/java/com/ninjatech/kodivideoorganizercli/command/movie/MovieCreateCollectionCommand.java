package com.ninjatech.kodivideoorganizercli.command.movie;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.movie.MovieCollection;

@Component
public class MovieCreateCollectionCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_CREATE_COLLECTION;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Creates a brand new Movie Collection";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        String name = joinArgs(commandLine);
        Path folder = this.environmentManager.getBasePath()
                                             .resolve(name);
        try {
            Files.createDirectory(folder);
            MovieCollection movieCollection = new MovieCollection(name, folder);
            this.environmentManager.selectMovieCollection(movieCollection);
        }
        catch (FileAlreadyExistsException e) {
            outputChannel.appendLine("Directory %s already exists",
                                     folder.toAbsolutePath()
                                           .toString());
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length > 0;
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        if (this.environmentManager.getBasePath() == null) {
            outputChannel.appendLine("Base Path not set");
            return false;
        }

        return true;
    }

}
