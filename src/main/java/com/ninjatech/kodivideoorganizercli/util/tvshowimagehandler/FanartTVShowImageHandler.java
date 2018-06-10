package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class FanartTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getFanarts()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getFanarts();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "fanart";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.FANART;
    }

    @Override
    protected Integer getColumns() {
        return 3;
    }

}
