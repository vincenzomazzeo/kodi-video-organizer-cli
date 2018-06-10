package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EnumMap;
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
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.AbstractTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.BannerTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.CharacterTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.ClearartTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.FanartTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.LandscapeTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.LogoTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.PosterTVShowImageHandler;
import com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler.SeasonFolderTVShowImageHandler;

@Component
public class TVShowSetImageCommand extends AbstractTVShowMandatoryCommand {

    private static final EnumMap<TVShowImageType, AbstractTVShowImageHandler> HANDLERS = new EnumMap<TVShowImageType, AbstractTVShowImageHandler>(TVShowImageType.class) {

        private static final long serialVersionUID = 1L;

        {
            put(TVShowImageType.BANNER, new BannerTVShowImageHandler());
            put(TVShowImageType.CHARACTER, new CharacterTVShowImageHandler());
            put(TVShowImageType.CLEARART, new ClearartTVShowImageHandler());
            put(TVShowImageType.FANART, new FanartTVShowImageHandler());
            put(TVShowImageType.LANDSCAPE, new LandscapeTVShowImageHandler());
            put(TVShowImageType.LOGO, new LogoTVShowImageHandler());
            put(TVShowImageType.POSTER, new PosterTVShowImageHandler());
            put(TVShowImageType.SEASON_FOLDER, new SeasonFolderTVShowImageHandler());
        }

    };

    @Override
    public Command getCommand() {
        return Command.TV_SHOW_SET_IMAGE;
    }

    @Override
    public Set<Type> getTypes() {
        return EnumSet.of(Type.TV_SHOW);
    }

    @Override
    public String getShortDescription() {
        return "Sets an Image";
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
        AbstractTVShowImageHandler handler = TVShowSetImageCommand.HANDLERS.get(imageType);

        TVShow tvShow = this.environmentManager.getSelectedTVShow();
        if (handler.hasImages(tvShow)) {
            Path path = handler.getImage(this.environmentManager,
                                         outputChannel,
                                         tvShow);
            if (path != null) {
                Path target = tvShow.getFolder()
                                    .resolve(String.format(imageType.getFileName(),
                                                           imageType == TVShowImageType.SEASON_FOLDER ? tvShow.getSelectedSeason()
                                                                                                              .getNumber()
                                                                                                      : ""));
                Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        else {
            outputChannel.appendLine("No Images found");
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
            if (!this.environmentManager.getFanartTVConnector()
                                        .isActive()) {
                outputChannel.appendLine("FanartTV is not active");
                result = false;
            }
            else {
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
        }

        return result;
    }

}
