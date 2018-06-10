package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ninjatech.kodivideoorganizercli.EnvironmentManager;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;
import com.ninjatech.kodivideoorganizercli.ui.ImageChoiceDialog;

public abstract class AbstractTVShowImageHandler {

    private static final Path DEFAULT_PATH = EnvironmentManager.BASE_PATH.resolve("tvshowimages");

    public abstract boolean hasImages(TVShow tvShow);

    protected abstract List<String> getUrls(TVShow tvShow);

    protected abstract String getFolder(TVShow tvShow);

    protected abstract TVShowImageType getImageType();

    protected abstract Integer getColumns();

    public Path getImage(EnvironmentManager environment,
                         CommandOutputChannel outputChannel,
                         TVShow tvShow) throws IOException {
        Path result = null;

        Path basePath = handleFolders(tvShow);

        List<Path> paths = Files.list(basePath)
                                .collect(Collectors.toList());
        if (paths.isEmpty()) {
            paths = new LinkedList<>();
            List<String> urls = getUrls(tvShow);
            for (String url : urls) {
                UriComponents uri = UriComponentsBuilder.fromUriString(url)
                                                        .build()
                                                        .encode();
                List<String> segments = uri.getPathSegments();
                String filename = segments.get(segments.size() - 1);
                Path path = basePath.resolve(filename);
                paths.add(path);
                outputChannel.appendLine("  Downloading %s", filename);
                environment.getFanartTVConnector()
                           .downloadImage(environment.getRestTemplate(), uri.toUri(), path);
            }
        }

        if (!paths.isEmpty()) {
            ImageChoiceDialog dialog = new ImageChoiceDialog(null);
            dialog.setImages(paths, getImageType(), getColumns());
            dialog.setVisible(true);
            result = dialog.getSelectedPath();
        }

        return result;
    }

    private Path handleFolders(TVShow tvShow) throws IOException {
        Path result = null;

        if (!Files.exists(AbstractTVShowImageHandler.DEFAULT_PATH)) {
            Files.createDirectories(AbstractTVShowImageHandler.DEFAULT_PATH);
        }
        Path basePath = AbstractTVShowImageHandler.DEFAULT_PATH.resolve(tvShow.getId()
                                                                              .toString())
                                                               .resolve(getFolder(tvShow));
        if (!Files.exists(basePath)) {
            result = Files.createDirectories(basePath);
        }
        else {
            result = basePath;
        }

        return result;
    }

}
