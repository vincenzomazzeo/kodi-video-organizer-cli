package com.ninjatech.kodivideoorganizercli.expander;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import com.ninjatech.kodivideoorganizercli.command.Command;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class DirectoryCommandExpander extends AbstractCommandExpander<File> {

    protected DirectoryCommandExpander(String input,
                                       OutputComponent outputComponent,
                                       Command command,
                                       Function<File, String> mapper) {
        super(input, outputComponent, command, mapper);
    }

    @Override
    protected Stream<File> getStream() {
        Stream<File> result = Stream.empty();

        File file = new File(this.input);
        File parent = null;
        if (file.exists() && file.isDirectory()) {
            parent = file;
        }
        else {
            file = file.getParentFile();
            if (file.exists() && file.isDirectory()) {
                parent = file;
            }
        }

        if (parent != null) {
            result = Arrays.stream(parent.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }

            }));
        }

        return result;
    }

}
