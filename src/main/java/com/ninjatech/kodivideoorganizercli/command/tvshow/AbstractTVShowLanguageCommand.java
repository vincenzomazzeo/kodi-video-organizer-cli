package com.ninjatech.kodivideoorganizercli.command.tvshow;

import java.util.Locale;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ninjatech.kodivideoorganizercli.command.AbstractCommand;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;
import com.ninjatech.kodivideoorganizercli.settings.SettingsHandler;

public abstract class AbstractTVShowLanguageCommand extends AbstractCommand {

    private static final String LANG = "lang";

    @Autowired
    private SettingsHandler settingsHandler;

    @Override
    public boolean isValid(CommandLine commandLine, CommandOutputChannel outputChannel) {
        return StringUtils.isBlank(commandLine.getOptionValue(AbstractTVShowLanguageCommand.LANG)) ||
               commandLine.getOptionValue(AbstractTVShowLanguageCommand.LANG)
                          .length() == 2;
    }

    @Override
    protected void setOptions(Options options) {
        options.addOption(Option.builder(AbstractTVShowLanguageCommand.LANG)
                                .desc("Language")
                                .hasArg(true)
                                .numberOfArgs(1)
                                .required(false)
                                .type(String.class)
                                .build());
    }

    protected Locale getLanguage(CommandLine commandLine) {
        return StringUtils.isBlank(commandLine.getOptionValue(AbstractTVShowLanguageCommand.LANG)) ? new Locale(this.settingsHandler.getTheTVDBCom()
                                                                                                                                    .getLanguage())
                                                                                                   : new Locale(commandLine.getOptionValue(AbstractTVShowLanguageCommand.LANG));
    }

}
