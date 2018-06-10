package com.ninjatech.kodivideoorganizercli.command.movie;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.movie.Movie;
import com.ninjatech.kodivideoorganizercli.util.MovieUtils;

@Component
public class MovieSearchCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_SEARCH;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Searches for a Movie";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Movie movie = MovieUtils.search(this.environmentManager,
                                        inputChannel,
                                        outputChannel,
                                        joinArgs(commandLine));
        if (movie != null) {
            movie.setSortTitle(askFor(inputChannel,
                                      outputChannel,
                                      "Sort Title",
                                      StringUtils.deleteWhitespace(movie.getTitle())));
            movie.setOutline(askFor(inputChannel, outputChannel, "Outline", movie.getOutline()));
            movie.setPlot(askFor(inputChannel, outputChannel, "Plot", movie.getPlot()));
            handleMovie(movie, outputChannel);
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length > 0;
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        if (!this.environmentManager.getTheMovieDBConnector()
                                    .isActive()) {
            outputChannel.appendLine("TheMovieDB is not active");
            return false;
        }
        if (this.environmentManager.getBasePath() == null) {
            outputChannel.appendLine("Base Path not set");
            return false;
        }

        return true;
    }

    private String askFor(CommandInputChannel inputChannel,
                          CommandOutputChannel outputChannel,
                          String type,
                          String def) throws Exception {
        outputChannel.appendLine(String.format("Please enter %s [%s]", type, def));
        String input = inputChannel.take();
        return StringUtils.isBlank(input) ? def : input;
    }

    private void handleMovie(Movie movie, CommandOutputChannel outputChannel) {
        Path folder = null;
        if (this.environmentManager.getSelectedMovieCollection() != null) {
            folder = this.environmentManager.getSelectedMovieCollection()
                                            .getFolder()
                                            .resolve(MovieUtils.getFolderName(movie));
        }
        else {
            folder = this.environmentManager.getBasePath()
                                            .resolve(MovieUtils.getFolderName(movie));
        }

        if (Files.exists(folder)) {
            outputChannel.appendLine("Movie already present");
        }

        try {
            folder = Files.createDirectory(folder);
            movie.setFolder(folder);
            if (this.environmentManager.getSelectedMovieCollection() != null) {
                movie.setSet(this.environmentManager.getSelectedMovieCollection()
                                                    .getName());
            }
            MovieUtils.writeInfoFile(movie);
            this.environmentManager.selectMovie(movie);
            outputChannel.appendLine("'%s' created and selected", movie.getTitle());
        }
        catch (Exception e) {
            outputChannel.appendError("Failed to create Movie");
            e.printStackTrace();
        }
    }

}
