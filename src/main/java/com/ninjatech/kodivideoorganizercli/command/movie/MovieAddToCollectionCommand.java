package com.ninjatech.kodivideoorganizercli.command.movie;

import java.nio.file.Files;
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
import com.ninjatech.kodivideoorganizercli.model.movie.MovieCollection;
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;
import com.ninjatech.kodivideoorganizercli.util.MovieUtils;

@Component
public class MovieAddToCollectionCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_ADD_TO_COLLECTION;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Adds a Movie to the selected Collection";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Path basePath = this.environmentManager.getBasePath();
        String prefix = StringUtils.EMPTY;
        List<Path> folders = MovieUtils.getMoviePathList(basePath, prefix);
        Chooser<Path> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT,
                                                         Chooser.ChooserType.SKIP),
                                              inputChannel,
                                              outputChannel,
                                              e -> e.getFileName()
                                                    .toString());
        Chooser<Path>.ChooserResult chooserResult = chooser.execute(folders,
                                                                    "Choose Movie to Add to the Selected Collection");
        if (chooserResult.getType() == ChooserResultType.CHOOSEN) {
            Path folder = chooserResult.getElement();
            MovieCollection movieCollection = this.environmentManager.getSelectedMovieCollection();
            Path destination = movieCollection.getFolder()
                                              .resolve(folder.getFileName()
                                                             .toString());
            Files.move(folder, destination);
            Movie movie = MovieUtils.fetch(this.environmentManager, inputChannel, outputChannel, destination);
            movie.setFolder(destination);
            movie.setSet(this.environmentManager.getSelectedMovieCollection()
                                                .getName());
            MovieUtils.writeInfoFile(movie);
        }
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        if (this.environmentManager.getBasePath() == null) {
            outputChannel.appendLine("Base Path not set");
            return false;
        }

        MovieCollection movieCollection = this.environmentManager.getSelectedMovieCollection();

        if (movieCollection == null) {
            outputChannel.appendLine("No Collection selected");
            return false;
        }

        return true;
    }

}
