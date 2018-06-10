package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class PosterTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getPosters()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getPosters();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "poster";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.POSTER;
    }

    @Override
    protected Integer getColumns() {
        return 5;
    }

}
