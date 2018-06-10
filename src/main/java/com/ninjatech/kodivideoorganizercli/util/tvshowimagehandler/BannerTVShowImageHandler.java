package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class BannerTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getBanners()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getBanners();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "banner";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.BANNER;
    }

    @Override
    protected Integer getColumns() {
        return 2;
    }

}
