package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class LogoTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getLogos()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getLogos();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "logo";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.LOGO;
    }

    @Override
    protected Integer getColumns() {
        return 4;
    }

}
