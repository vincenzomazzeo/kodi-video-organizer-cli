package com.ninjatech.kodivideoorganizercli.command;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import com.ninjatech.kodivideoorganizercli.EnvironmentManager;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.input.InputHandler;
import com.ninjatech.kodivideoorganizercli.model.movie.Movie;
import com.ninjatech.kodivideoorganizercli.model.movie.MovieCollection;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.output.OutputMessageBuilder;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;
import com.ninjatech.kodivideoorganizercli.ui.StatusComponent;

public class CommandHandler {

    private final EnvironmentManager environmentManager;
    private final CommandRepository commandRepository;
    private final CommandLineParser commandLineParser;
    private final InputHandler inputHandler;
    private final OutputComponent outputComponent;
    private final StatusComponent statusComponent;
    private final Executor executor;
    private CommandInputChannel commandInputChannel;

    public CommandHandler(EnvironmentManager environmentManager,
                          CommandRepository commandRepository,
                          CommandLineParser commandLineParser,
                          InputHandler inputHandler,
                          OutputComponent outputComponent,
                          StatusComponent statusComponent) {
        this.environmentManager = environmentManager;
        this.commandRepository = commandRepository;
        this.commandLineParser = commandLineParser;
        this.inputHandler = inputHandler;
        this.outputComponent = outputComponent;
        this.statusComponent = statusComponent;
        this.executor = Executors.newSingleThreadExecutor();
        this.commandInputChannel = null;

        this.inputHandler.registerCommandHandler(this);
    }

    public void notifyInput(String input) {
        if (this.commandInputChannel != null) {
            this.commandInputChannel.add(input);
        }
        else {
            if (StringUtils.isNotBlank(input)) {
                try {
                    String[] splittedCommand = input.split(" ");
                    AbstractCommand command = parseAndGetCommand(splittedCommand[0]);
                    if (command != null) {
                        CommandLine commandLine = getCommandLine(command, splittedCommand);
                        CommandOutputChannel commandOutputChannel = new CommandOutputChannel(this.outputComponent);
                        if (checkCommand(command, commandLine, commandOutputChannel)) {
                            this.inputHandler.setHistoryEnabled(false);
                            this.commandInputChannel = new CommandInputChannel();
                            CommandExecutor commandExecutor = new CommandExecutor(this,
                                                                                  command,
                                                                                  commandLine,
                                                                                  this.commandInputChannel,
                                                                                  commandOutputChannel);
                            this.executor.execute(commandExecutor);
                        }
                    }
                    else {
                        this.outputComponent.appendLine("Command not found");
                    }
                }
                catch (Exception e) {
                    this.outputComponent.appendError(e);
                    e.printStackTrace();
                }
            }
        }
    }

    protected void notifyCommandCompletition() {
        this.commandInputChannel = null;
        this.inputHandler.setHistoryEnabled(true);

        String basePath = this.environmentManager.getBasePath() != null ? this.environmentManager.getBasePath()
                                                                                                 .toString()
                                                                        : StringUtils.EMPTY;
        String mode = this.commandRepository.getActiveType()
                                            .isPrintable() ? this.commandRepository.getActiveType()
                                                                                   .getDesc()
                                                           : StringUtils.EMPTY;
        String elementData = StringUtils.EMPTY;
        String subelementData = StringUtils.EMPTY;
        if (this.commandRepository.getActiveType() == Type.TV_SHOW) {
            TVShow tvShow = this.environmentManager.getSelectedTVShow();
            if (tvShow != null) {
                elementData = tvShow.getName();
                subelementData = tvShow.getSelectedSeason() != null ? tvShow.getSelectedSeason()
                                                                            .getName()
                                                                    : StringUtils.EMPTY;
            }
        }
        else if (this.commandRepository.getActiveType() == Type.MOVIE) {
            MovieCollection movieCollection = this.environmentManager.getSelectedMovieCollection();
            if (movieCollection != null) {
                elementData = movieCollection.getName();
            }
            Movie movie = this.environmentManager.getSelectedMovie();
            if (movie != null) {
                subelementData = movie.getTitle();
            }
        }
        final String element = elementData;
        final String subelement = subelementData;

        SwingUtilities.invokeLater(() -> {
            this.outputComponent.append(OutputMessageBuilder.outputMessageBuilder()
                                                            .brightGreen("OK\n"));
            this.outputComponent.appendEmptyLine();
            this.statusComponent.setBasePath(basePath);
            this.statusComponent.setMode(mode);
            this.statusComponent.setElement(element);
            this.statusComponent.setSubelement(subelement);
        });
    }

    private AbstractCommand parseAndGetCommand(String commandName) {
        AbstractCommand result = null;

        Command command = Command.parse(this.commandRepository.getAvailableCommands(), commandName);
        if (command != null) {
            result = this.commandRepository.getCommand(command);
        }

        return result;
    }

    private CommandLine getCommandLine(AbstractCommand command, String[] splittedCommand) throws ParseException {
        String[] args = splittedCommand.length > 1 ? Arrays.copyOfRange(splittedCommand,
                                                                        1,
                                                                        splittedCommand.length)
                                                   : new String[0];
        return this.commandLineParser.parse(command.getOptions(), args);
    }

    private boolean checkCommand(AbstractCommand command,
                                 CommandLine commandLine,
                                 CommandOutputChannel commandOutputChannel) {
        boolean result = false;

        if (command.isValid(commandLine, commandOutputChannel)) {
            if (command.checkConstraints(commandLine, commandOutputChannel)) {
                result = true;
            }
        }
        else {
            command.printHelp(commandOutputChannel);
        }

        return result;
    }

}
