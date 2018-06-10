package com.ninjatech.kodivideoorganizercli.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.ninjatech.kodivideoorganizercli.EnvironmentManager;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.connector.fanarttv.model.SerieImages;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Episode;
import com.ninjatech.kodivideoorganizercli.connector.thetvdbcom.model.Serie;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShow;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowEpisode;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowSeason;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;

public class TVShowUtils {

    public static final String SEASON_FOLDER_FORMAT = "Season %s";
    private static final String FULL_EPISODE_NAME_FORMAT = "%s - %dx%02d - %s";
    private static final String EPISODE_NAME_FORMAT = "%02d - %s";

    private static final String[] VIDEO_FILE_EXTENSIONS = new String[] { "webm", "mkv", "flv", "vob", "ogv", "ogg",
                                                                         "drc", "mng", "avi", "mov", "qt", "wmv", "yuv",
                                                                         "rm", "rmvb", "asf", "mp4", "m4p", "m4v",
                                                                         "mpg", "mp2", "mpeg", "mpe", "mpv", "m2v",
                                                                         "svi", "3gp", "3g2", "mxf", "roq", "nsv" };

    private TVShowUtils() {}

    public static TVShow search(EnvironmentManager environment,
                                CommandInputChannel inputChannel,
                                CommandOutputChannel outputChannel,
                                String query,
                                Locale language) throws Exception {
        TVShow result = null;

        outputChannel.appendLine("Querying TheTVDB.com for %s", query);
        List<Serie> series = environment.getTheTVDBComConnector()
                                        .search(environment.getRestTemplate(), query, language);
        if (series.isEmpty()) {
            outputChannel.appendLine("No TV Show found");
        }
        else {
            Chooser<Serie> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT),
                                                   inputChannel,
                                                   outputChannel,
                                                   e -> String.format("%s - %s - %s",
                                                                      e.getSeriesName(),
                                                                      e.getFirstAired(),
                                                                      e.getStatus()));
            Chooser<Serie>.ChooserResult chooserResult = chooser.execute(series, "TV Show");
            if (chooserResult.getType() == ChooserResultType.CHOOSEN) {
                Serie serie = chooserResult.getElement();
                result = new TVShow(serie.getId(), serie.getSeriesName(), serie.getFirstAired(), serie.getStatus());
                addImagesToTVShow(environment, outputChannel, result);
            }
        }

        return result;
    }

    public static void setSeasons(EnvironmentManager environment, TVShow tvShow) {
        if (tvShow.getSeasons()
                  .isEmpty()) {
            List<String> seasons = environment.getTheTVDBComConnector()
                                              .seasons(environment.getRestTemplate(),
                                                       tvShow.getId()
                                                             .toString());
            seasons.forEach(tvShow::addSeason);
        }
    }

    public static void setEpisodes(EnvironmentManager environment,
                                   TVShowSeason season,
                                   Locale language) throws IOException {
        if (season.getEpisodes()
                  .isEmpty()) {
            List<Episode> episodes = environment.getTheTVDBComConnector()
                                                .episodes(environment.getRestTemplate(),
                                                          season.getTvShow()
                                                                .getId()
                                                                .toString(),
                                                          season.getNumber()
                                                                .toString(),
                                                          language);
            List<Path> files = getVideoFiles(season);
            for (Episode episode : episodes) {
                TVShowEpisode tvShowEpisode = season.addEpisode(episode.getId(),
                                                                episode.getAiredEpisodeNumber(),
                                                                episode.getEpisodeName());
                String fileName = String.format(TVShowUtils.FULL_EPISODE_NAME_FORMAT,
                                                season.getTvShow()
                                                      .getName(),
                                                season.getNumber(),
                                                episode.getAiredEpisodeNumber(),
                                                episode.getEpisodeName());
                Path file = files.stream()
                                 .filter(e -> e.getFileName()
                                               .toString()
                                               .startsWith(fileName))
                                 .findFirst()
                                 .orElse(null);
                tvShowEpisode.setFile(file);
            }
        }
    }

    public static String getSeasonWithFolderCheck(TVShowSeason season) {
        return String.format("%s%s",
                             season.getName(),
                             Files.exists(season.getFolder()) ? "*" : "");
    }

    public static String getEpisodeName(TVShowEpisode episode) {
        return String.format(TVShowUtils.EPISODE_NAME_FORMAT, episode.getNumber(), episode.getName());
    }

    public static String getFullEpisodeName(TVShowEpisode episode) {
        return String.format(TVShowUtils.FULL_EPISODE_NAME_FORMAT,
                             episode.getSeason()
                                    .getTvShow()
                                    .getName(),
                             episode.getSeason()
                                    .getNumber(),
                             episode.getNumber(),
                             episode.getName());
    }

    public static String getFullMultiEpisodeName(List<TVShowEpisode> episodes) {
        String result = getFullEpisodeName(episodes.get(0));

        for (int i = 1, n = episodes.size(); i < n; i++) {
            TVShowEpisode episode = episodes.get(i);
            result = String.format(TVShowUtils.FULL_EPISODE_NAME_FORMAT,
                                   result,
                                   episode.getSeason()
                                          .getNumber(),
                                   episode.getNumber(),
                                   episode.getName());
        }

        return result;
    }

    public static String getEpisodeNameWithFileCheck(TVShowEpisode episode, Integer length) {
        Path file = episode.getFile();
        String episodeName = String.format(TVShowUtils.EPISODE_NAME_FORMAT, episode.getNumber(), episode.getName());
        if (file != null && Files.exists(file)) {
            return String.format("%-".concat(String.valueOf(length + 5))
                                     .concat("s [%s]"),
                                 episodeName,
                                 file.getFileName()
                                     .toString());
        }
        else {
            return episodeName;
        }
    }

    public static List<Path> getVideoFiles(TVShowSeason season) throws IOException {
        return Files.list(season.getFolder())
                    .filter(e -> !Files.isDirectory(e)
                                 && Arrays.stream(TVShowUtils.VIDEO_FILE_EXTENSIONS)
                                          .anyMatch(ext -> StringUtils.endsWithIgnoreCase(e.getFileName()
                                                                                           .toString(),
                                                                                          ext)))
                    .collect(Collectors.toList());
    }

    public static List<Path> getUnboundFiles(TVShowSeason season) throws IOException {
        List<Path> result = TVShowUtils.getVideoFiles(season);
        List<TVShowEpisode> episodes = season.getEpisodes();
        return result.stream()
                     .filter(f -> episodes.stream()
                                          .noneMatch(e -> e.getFile() != null && e.getFile()
                                                                                  .getFileName()
                                                                                  .equals(f.getFileName())))
                     .collect(Collectors.toList());
    }

    public static List<TVShowEpisode> getUnboundEpisodes(TVShowSeason season) {
        return season.getEpisodes()
                     .stream()
                     .filter(e -> e.getFile() == null)
                     .collect(Collectors.toList());
    }

    private static void addImagesToTVShow(EnvironmentManager environment,
                                          CommandOutputChannel outputChannel,
                                          TVShow tvShow) {
        if (environment.getFanartTVConnector()
                       .isActive()) {
            outputChannel.appendLine("Querying FanartTV for %s images", tvShow.getName());
            SerieImages serieImages = environment.getFanartTVConnector()
                                                 .serieImages(environment.getRestTemplate(),
                                                              tvShow.getId()
                                                                    .toString());
            if (serieImages.getTvBanners() != null) {
                serieImages.getTvBanners()
                           .forEach(e -> tvShow.addBanner(e.getUrl()));
            }
            if (serieImages.getCharacterArts() != null) {
                serieImages.getCharacterArts()
                           .forEach(e -> tvShow.addCharacter(e.getUrl()));
            }
            if (serieImages.getHdClearArts() != null) {
                serieImages.getHdClearArts()
                           .forEach(e -> tvShow.addClearart(e.getUrl()));
            }
            if (serieImages.getShowBackgrounds() != null) {
                serieImages.getShowBackgrounds()
                           .forEach(e -> tvShow.addFanart(e.getUrl()));
            }
            if (serieImages.getTvThumbs() != null) {
                serieImages.getTvThumbs()
                           .forEach(e -> tvShow.addLandscape(e.getUrl()));
            }
            if (serieImages.getHdTVLogos() != null) {
                serieImages.getHdTVLogos()
                           .forEach(e -> tvShow.addLogo(e.getUrl()));
            }
            if (serieImages.getTvPosters() != null) {
                serieImages.getTvPosters()
                           .forEach(e -> tvShow.addPoster(e.getUrl()));
            }
            if (serieImages.getSeasonPosters() != null) {
                serieImages.getSeasonPosters()
                           .forEach(e -> tvShow.addSeasonImage(Integer.valueOf(e.getSeason()), e.getUrl()));
            }
        }
    }

}
