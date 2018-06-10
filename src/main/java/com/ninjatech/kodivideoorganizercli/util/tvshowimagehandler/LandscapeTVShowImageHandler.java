package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class LandscapeTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getLandscapes()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getLandscapes();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "landscape";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.LANDSCAPE;
    }

    @Override
    protected Integer getColumns() {
        return 3;
    }

}
