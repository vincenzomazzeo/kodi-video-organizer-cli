package com.ninjatech.kodivideoorganizercli.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import com.ninjatech.kodivideoorganizercli.EnvironmentManager;
import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.MovieExternalId;
import com.ninjatech.kodivideoorganizercli.model.movie.Movie;
import com.ninjatech.kodivideoorganizercli.util.Chooser.ChooserResultType;

public class MovieUtils {

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "movie")
    @XmlType(propOrder = { "title", "sortTitle", "set", "outline", "plot" })
    private static class MovieXMLScaffold {

        @XmlElement
        private String title;
        @XmlElement
        private String outline;
        @XmlElement
        private String plot;
        @XmlElement
        private String sortTitle;
        @XmlElement
        private String set;
        private transient String imdbId;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOutline() {
            return this.outline;
        }

        public void setOutline(String outline) {
            this.outline = outline;
        }

        public String getPlot() {
            return this.plot;
        }

        public void setPlot(String plot) {
            this.plot = plot;
        }

        public String getSortTitle() {
            return this.sortTitle;
        }

        public void setSortTitle(String sortTitle) {
            this.sortTitle = sortTitle;
        }

        public String getSet() {
            return this.set;
        }

        public void setSet(String set) {
            this.set = set;
        }

    }

    private static final String FOLDER_NAME_FORMAT = "%s (%s)";
    private static final String INFO_FILE_NAME_FORMAT = "%s (%s).nfo";
    private static final String INFO_FULL_FILE_NAME_FORMAT = "%s.nfo";
    private static final String IMDB_FORMAT = "http://www.imdb.com/title/%s/";
    private static final Pattern IMDB_PATTERN = Pattern.compile("http://www.imdb.com/title/(?<imdbId>tt[0-9]{7,7})/");
    private static final String[] VIDEO_FILE_EXTENSIONS = new String[] { "webm", "mkv", "flv", "vob", "ogv", "ogg",
                                                                         "drc", "mng", "avi", "mov", "qt", "wmv", "yuv",
                                                                         "rm", "rmvb", "asf", "mp4", "m4p", "m4v",
                                                                         "mpg", "mp2", "mpeg", "mpe", "mpv", "m2v",
                                                                         "svi", "3gp", "3g2", "mxf", "roq", "nsv" };
    private static JAXBContext jaxbContext = null;

    private MovieUtils() {}

    public static Movie search(EnvironmentManager environment,
                               CommandInputChannel inputChannel,
                               CommandOutputChannel outputChannel,
                               String query) throws Exception {
        Movie result = null;

        outputChannel.appendLine("Querying TheMovieDB for %s", query);
        List<com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie> movies = environment.getTheMovieDBConnector()
                                                                                                       .search(environment.getRestTemplate(),
                                                                                                               query);
        if (movies.isEmpty()) {
            outputChannel.appendLine("No Movie found");
        }
        else {
            Chooser<com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie> chooser = new Chooser<>(EnumSet.of(Chooser.ChooserType.ABORT),
                                                                                                                  inputChannel,
                                                                                                                  outputChannel,
                                                                                                                  e -> String.format("%s - %s",
                                                                                                                                     e.getTitle(),
                                                                                                                                     e.getReleaseDate()));
            Chooser<com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie>.ChooserResult chooserResult = chooser.execute(movies,
                                                                                                                                        "Movie");
            if (chooserResult.getType() == ChooserResultType.CHOOSEN) {
                com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie movie = chooserResult.getElement();

                MovieExternalId externalId = environment.getTheMovieDBConnector()
                                                        .movieExternalId(environment.getRestTemplate(), movie.getId());
                if (externalId == null || StringUtils.isBlank(externalId.getImdbId())) {
                    outputChannel.appendLine("IMDB Id not found for %s (%d)", movie.getTitle(), movie.getId());
                }
                else {
                    result = new Movie(movie.getId(),
                                       movie.getTitle(),
                                       getYear(movie),
                                       movie.getOverview(),
                                       movie.getOverview(),
                                       externalId.getImdbId());
                }
            }
        }

        return result;
    }

    public static Movie fetch(EnvironmentManager environment,
                              CommandInputChannel inputChannel,
                              CommandOutputChannel outputChannel,
                              Path path) throws Exception {
        Movie result = null;

        String folderName = path.getFileName()
                                .toString();
        Path infoFile = path.resolve(String.format(MovieUtils.INFO_FULL_FILE_NAME_FORMAT, folderName));
        MovieXMLScaffold movieXMLScaffold = readInfoFile(infoFile);

        outputChannel.appendLine("Querying TheMovieDB for %s", movieXMLScaffold.imdbId);
        com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie movie = environment.getTheMovieDBConnector()
                                                                                                .getByExternalId(environment.getRestTemplate(),
                                                                                                                 movieXMLScaffold.imdbId);
        if (movie == null) {
            outputChannel.appendLine("No Movie found");
        }
        else {
            result = new Movie(movie.getId(),
                               StringEscapeUtils.unescapeHtml4(movieXMLScaffold.getTitle()),
                               getYear(movie),
                               StringEscapeUtils.unescapeHtml4(movieXMLScaffold.getOutline()),
                               StringEscapeUtils.unescapeHtml4(movieXMLScaffold.getPlot()),
                               movieXMLScaffold.imdbId);
            result.setSortTitle(StringEscapeUtils.unescapeHtml4(movieXMLScaffold.getSortTitle()));
            result.setSet(StringEscapeUtils.unescapeHtml4(movieXMLScaffold.getSet()));
        }

        return result;
    }

    public static String getFolderName(Movie movie) {
        return String.format(MovieUtils.FOLDER_NAME_FORMAT, Utils.adaptName(movie.getTitle()), movie.getYear());
    }

    public static void writeInfoFile(Movie movie) throws Exception {
        if (MovieUtils.jaxbContext == null) {
            MovieUtils.jaxbContext = JAXBContext.newInstance(MovieXMLScaffold.class);
        }

        MovieXMLScaffold scaffold = new MovieXMLScaffold();
        scaffold.setTitle(StringEscapeUtils.escapeHtml4(movie.getTitle()));
        scaffold.setOutline(StringEscapeUtils.escapeHtml4(movie.getOutline()));
        scaffold.setPlot(StringEscapeUtils.escapeHtml4(movie.getPlot()));
        scaffold.setSortTitle(movie.getSortTitle());
        scaffold.setSet(movie.getSet());

        Path info = movie.getFolder()
                         .resolve(String.format(MovieUtils.INFO_FILE_NAME_FORMAT,
                                                Utils.adaptName(movie.getTitle()),
                                                movie.getYear()));
        Files.deleteIfExists(info);
        Files.createFile(info);

        try (FileWriter fileWriter = new FileWriter(info.toFile())) {
            Marshaller jaxbMarshaller = MovieUtils.jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(scaffold, fileWriter);
            fileWriter.append(String.format(MovieUtils.IMDB_FORMAT, movie.getImdbId()));
            fileWriter.flush();
        }
    }

    public static List<Path> getMoviePathList(Path basePath, String prefix) throws Exception {
        List<Path> result = new LinkedList<>();

        List<Path> folders = Files.list(basePath)
                                  .collect(Collectors.toList());
        for (Path folder : folders) {
            if (Files.isDirectory(folder)
                && StringUtils.startsWithIgnoreCase(folder.getFileName()
                                                          .toString(),
                                                    prefix)
                && Files.list(folder)
                        .anyMatch(e -> e.getFileName()
                                        .toString()
                                        .equals(String.format("%s.nfo",
                                                              folder.getFileName()
                                                                    .toString())))) {
                result.add(folder);
            }
        }

        return result;
    }

    public static List<Path> getUnboundFiles(Movie movie) throws IOException {
        return Files.list(movie.getFolder())
                    .filter(e -> !Files.isDirectory(e)
                                 && Arrays.stream(MovieUtils.VIDEO_FILE_EXTENSIONS)
                                          .anyMatch(ext -> StringUtils.endsWithIgnoreCase(e.getFileName()
                                                                                           .toString(),
                                                                                          ext)))
                    .collect(Collectors.toList());
    }

    public static void renameFile(Path file, Movie movie, Integer part) throws IOException {
        String folderName = getFolderName(movie);
        String extension = FilenameUtils.getExtension(file.getFileName()
                                                          .toString());
        Path newFile = file.getParent()
                           .resolve(String.format("%s%s.%s",
                                                  folderName,
                                                  part != null ? " part".concat(part.toString()) : "",
                                                  extension));
        Files.move(file, newFile);
    }

    private static MovieXMLScaffold readInfoFile(Path infoFile) throws Exception {
        MovieXMLScaffold result = null;

        if (MovieUtils.jaxbContext == null) {
            MovieUtils.jaxbContext = JAXBContext.newInstance(MovieXMLScaffold.class);
        }

        String imdbId = null;
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(infoFile)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = MovieUtils.IMDB_PATTERN.matcher(line);
                if (matcher.matches()) {
                    imdbId = matcher.group("imdbId");
                }
                else {
                    data.append(line);
                }
            }
        }

        Unmarshaller jaxbUnmarshaller = MovieUtils.jaxbContext.createUnmarshaller();
        result = (MovieXMLScaffold) jaxbUnmarshaller.unmarshal(new StringReader(data.toString()));
        result.imdbId = imdbId;

        return result;
    }

    private static String getYear(com.ninjatech.kodivideoorganizercli.connector.themoviedb.model.Movie movie) {
        return movie.getReleaseDate()
                    .substring(0, 4);
    }

}
