package com.ninjatech.kodivideoorganizercli.command.movie;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
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
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;
import com.ninjatech.kodivideoorganizercli.util.MovieUtils;

@Component
public class MovieFetchCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_FETCH;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Binds a folder to the Movie fetching data";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Path basePath = this.environmentManager.getSelectedMovieCollection() == null ? this.environmentManager.getBasePath()
                                                                                     : this.environmentManager.getSelectedMovieCollection()
                                                                                                              .getFolder();
        String prefix = commandLine.getArgs().length > 0 ? joinArgs(commandLine) : StringUtils.EMPTY;
        List<Path> folders = MovieUtils.getMoviePathList(basePath, prefix);
        if (folders.isEmpty()) {
            outputChannel.appendLine("No Folder found");
        }
        else {
            handleFolders(folders, commandLine, inputChannel, outputChannel);
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length >= 0;
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

    private void handleFolders(List<Path> folders,
                               CommandLine commandLine,
                               CommandInputChannel inputChannel,
                               CommandOutputChannel outputChannel) throws Exception {
        Chooser<Path> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT),
                                              inputChannel,
                                              outputChannel,
                                              e -> e.getFileName()
                                                    .toString());
        Chooser<Path>.ChooserResult result = chooser.execute(folders, "Folder");
        if (result.getType() == ChooserResultType.CHOOSEN) {
            Path folder = result.getElement();
            if (folder != null) {
                Movie movie = MovieUtils.fetch(this.environmentManager,
                                               inputChannel,
                                               outputChannel,
                                               folder);
                if (movie != null) {
                    movie.setFolder(folder);
                    this.environmentManager.selectMovie(movie);
                }
            }
        }
    }

}
