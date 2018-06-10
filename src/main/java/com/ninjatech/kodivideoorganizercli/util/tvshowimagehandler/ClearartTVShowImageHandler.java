package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class ClearartTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getCleararts()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getCleararts();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "clearart";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.CLEARART;
    }

    @Override
    protected Integer getColumns() {
        return 3;
    }

}
