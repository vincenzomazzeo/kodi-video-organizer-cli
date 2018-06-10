package com.ninjatech.kodivideoorganizercli.util.tvshowimagehandler;

import java.util.List;

import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class CharacterTVShowImageHandler extends AbstractTVShowImageHandler {

    @Override
    public boolean hasImages(TVShow tvShow) {
        return !tvShow.getCharacters()
                      .isEmpty();
    }

    @Override
    protected List<String> getUrls(TVShow tvShow) {
        return tvShow.getCharacters();
    }

    @Override
    protected String getFolder(TVShow tvShow) {
        return "character";
    }

    @Override
    protected TVShowImageType getImageType() {
        return TVShowImageType.CHARACTER;
    }

    @Override
    protected Integer getColumns() {
        return 3;
    }

}
