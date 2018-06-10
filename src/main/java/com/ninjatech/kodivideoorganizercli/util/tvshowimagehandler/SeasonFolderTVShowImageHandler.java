package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class SeasonFolderTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        List<String> seasonPosters = tvShow.getSeasonPoster(tvShow.getSelectedSeason()
                                                                  .getNumber());
        return seasonPosters != null && !seasonPosters.isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getSeasonPoster(tvShow.getSelectedSeason()
                                            .getNumber());
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return String.format("season_%d",
                             tvShow.getSelectedSeason()
                                   .getNumber());
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.SEASON_FOLDER;
    }

    @Override
    protected Integer getColumns() {
        return 5;
    }

}
