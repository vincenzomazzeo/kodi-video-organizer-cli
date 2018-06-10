package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository.Type;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;
import com.ninjatech.kodivideoorganizercli.ui.ImageDialog;

@Component
public class TVShowShowImageCommand extends AbstractTVShowMandatoryCommand {

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_SHOW_IMAGE;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Shows the current TV Show Images";
    }

    @Override
    public void printFullDescription(CommandOutputChannel outputChannel) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(CommandLine commandLine,
                        CommandInputChannel inputChannel,
                        CommandOutputChannel outputChannel) throws Exception {
        TVShowImageType imageType = TVShowImageType.parse(commandLine.getArgs()[0]);
        TVShow tvShow = this.environmentManager.getSelectedTVShow();
        Path image = tvShow.getFolder()
                           .resolve(String.format(imageType.getFileName(),
                                                  imageType == TVShowImageType.SEASON_FOLDER ? tvShow.getSelectedSeason()
                                                                                                     .getNumber()
                                                                                             : ""));
        if (Files.exists(image)) {
            ImageDialog dialog = new ImageDialog(null);
            dialog.setImage(image, imageType);
            dialog.setVisible(true);
        }
        else {
            outputChannel.appendLine("Image not found");
        }
    }

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return commandLine.getArgs().length == 1;
    }

    @Override
    public boolean checkConstraints(CommandLine commandLine, CommandOutputChannel outputChannel) {
        boolean result = super.checkConstraints(commandLine, outputChannel);

        if (result) {
            TVShowImageType imageType = TVShowImageType.parse(commandLine.getArgs()[0]);
            if (imageType == null) {
                outputChannel.appendLine("Invalid Image Type ");
                result = false;
            }
            else if (imageType == TVShowImageType.SEASON_FOLDER) {
                TVShow tvShow = this.environmentManager.getSelectedTVShow();
                if (tvShow.getSelectedSeason() == null) {
                    outputChannel.appendLine("No Season selected");
                    result = false;
                }
            }
        }

        return result;
    }

}
