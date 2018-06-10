package com.ninjatech.kodivideoorganizercli;

import java.io.PrintStream;

import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.alee.laf.WebLookAndFeel;
import com.ninjatech.kodivideoorganizercli.ui.UI;

@SpringBootApplication
public class CLI implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(WebLookAndFeel::install);
        WebLookAndFeel.setDecorateFrames(true);
        WebLookAndFeel.setDecorateDialogs(true);

        new SpringApplicationBuilder(CLI.class).headless(false)
                                               .run(args);
    }

    public static void exit() {
        try {
            FileUtils.deleteDirectory(EnvironmentManager.BASE_PATH.toFile());
        }
        catch (Exception e) {}

        System.exit(0);
    }

    @Autowired
    private ErrorLog errorLog;
    @Autowired
    private UI ui;

    @Override
    public void run(String... args) throws Exception {
        System.setErr(new PrintStream(this.errorLog));
        this.ui.setVisible(true);
    }

}
