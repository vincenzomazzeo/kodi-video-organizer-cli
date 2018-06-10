package com.ninjatech.kodivideoorganizercli.command.movie;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.movie.MovieCollection;
import com.ninjatech.kodivideoorganizercli.util.Chooser;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;

@Component
public class MovieSetCollection extends AbstractCommand {

    @Override
    public Command getCommand() {
        return Command.MOVIE_SET_COLLECTION;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.MOVIE);
    }

    @Override
    public String getShortDescription() {
        return "Sets the Movie Collection";
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
        String prefix = commandLine.getArgs().length > 0 ? joinArgs(commandLine) : StringUtils.EMPTY;
        List<Path> folders = Files.list(basePath)
                                  .filter(e -> Files.isDirectory(e)
                                               && StringUtils.startsWithIgnoreCase(e.getFileName()
                                                                                    .toString(),
                                                                                   prefix))
                                  .collect(Collectors.toList());
        if (folders.isEmpty()) {
            outputChannel.appendLine("No Folder found");
        }
        else {
            handleFolders(folders, inputChannel, outputChannel);
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return true;
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        if (this.environmentManager.getBasePath() == null) {
            outputChannel.appendLine("Base Path not set");
            return false;
        }

        return true;
    }

    private void handleFolders(List<Path> folders,
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
                MovieCollection movieCollection = new MovieCollection(result.getElement()
                                                                            .getFileName()
                                                                            .toString(),
                                                                      result.getElement());
                this.environmentManager.selectMovieCollection(movieCollection);
            }
        }
    }

}
