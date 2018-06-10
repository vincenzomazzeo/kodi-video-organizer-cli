package com.ninjatech.kodivideoorganizercli.command.movie;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
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
public class MovieBindFilesCommand extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_BIND_FILES;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Binds Movie to File/s";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        Movie movie = this.environmentManager.getSelectedMovie();
        List<Path> unboundFiles = MovieUtils.getUnboundFiles(movie);
        if (unboundFiles.isEmpty()) {
            outputChannel.appendLine("No Video File to bind");
        }
        else {
            List<Path> files = new LinkedList<>();
            Chooser<Path>.ChooserResult chooserResult = null;
            do {
                Chooser<Path> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT, Chooser.ChooserType.SKIP),
                                                      inputChannel,
                                                      outputChannel,
                                                      e -> e.getFileName()
                                                            .toString());
                chooserResult = chooser.execute(unboundFiles, "Choose File to Bind");
                if (chooserResult.getType() == ChooserResultType.CHOOSEN) {
                    Path file = chooserResult.getElement();
                    files.add(file);
                    unboundFiles.remove(file);
                }
            }
            while (chooserResult.getType() == ChooserResultType.CHOOSEN && !unboundFiles.isEmpty());

            if (files.size() == 1) {
                MovieUtils.renameFile(files.get(0), movie, null);
            }
            else if (files.size() > 1) {
                for (int i = 0, n = files.size(); i < n; i++) {
                    MovieUtils.renameFile(files.get(i), movie, i + 1);
                }
            }
        }
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        boolean result = this.environmentManager.getSelectedMovie() != null;

        if (!result) {
            outputChannel.appendLine("No Movie selected");
        }

        return result;
    }

}
