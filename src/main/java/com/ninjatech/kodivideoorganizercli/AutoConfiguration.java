package com.ninjatech.kodivideoorganizercli;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjatech.kodivideoorganizercli.command.CommandHandler;
import com.ninjatech.kodivideoorganizercli.command.CommandRepository;
import com.ninjatech.kodivideoorganizercli.expander.ExpanderHandler;
import com.ninjatech.kodivideoorganizercli.input.InputHandler;
import com.ninjatech.kodivideoorganizercli.settings.SettingsHandler;
import com.ninjatech.kodivideoorganizercli.ui.InputComponent;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;
import com.ninjatech.kodivideoorganizercli.ui.StatusComponent;
import com.ninjatech.kodivideoorganizercli.ui.UI;

@Configuration
@ComponentScan("com.ninjatech.kodivideoorganizercli.command")
public class AutoConfiguration {

    @Bean
    private static CommandHandler commandHandler(EnvironmentManager environmentManager,
                                                 CommandRepository commandRepository,
                                                 CommandLineParser commandLineParser,
                                                 InputHandler inputHandler,
                                                 OutputComponent outputComponent,
                                                 StatusComponent statusComponent) {
        return new CommandHandler(environmentManager,
                                  commandRepository,
                                  commandLineParser,
                                  inputHandler,
                                  outputComponent,
                                  statusComponent);
    }

    @Bean
    private static CommandLineParser commandLineParser() {
        return new DefaultParser();
    }

    @Bean
    private static CommandRepository commandRepository() {
        return new CommandRepository();
    }

    @Bean
    private static EnvironmentManager environment(OutputComponent outputComponent,
                                                  SettingsHandler settingsHandler,
                                                  SSLConnectionSocketFactory sslConnectionSocketFactory) {
        return new EnvironmentManager(outputComponent, settingsHandler, sslConnectionSocketFactory);
    }

    @Bean
    private static ErrorLog errorLog() {
        return new ErrorLog();
    }

    @Bean
    private static ExpanderHandler expanderHandler(OutputComponent outputComponent,
                                                   CommandRepository commandRepository) {
        return new ExpanderHandler(outputComponent, commandRepository);
    }

    @Bean
    private static HelpFormatter helpFormatter() {
        return new HelpFormatter();
    }

    @Bean
    private static InputComponent inputComponent() {
        return new InputComponent();
    }

    @Bean
    private static InputHandler inputHandler(InputComponent inputComponent, ExpanderHandler expanderHandler) {
        return new InputHandler(inputComponent, expanderHandler);
    }

    @Bean
    private static ObjectMapper objectMapper() {
        ObjectMapper result = new ObjectMapper();

        result.setSerializationInclusion(Include.NON_EMPTY);

        return result;
    }

    @Bean
    private static OutputComponent outputComponent() {
        return new OutputComponent();
    }

    @Bean
    private static SettingsHandler settingsHandler(OutputComponent outputComponent,
                                                   ObjectMapper objectMapper) throws IOException {
        return new SettingsHandler(outputComponent, objectMapper);
    }

    @Bean
    private static SSLConnectionSocketFactory getSSLConnectionSocketFactory() throws Exception {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext;
        sslContext = org.apache.http.ssl.SSLContexts.custom()
                                                    .loadTrustMaterial(null, acceptingTrustStrategy)
                                                    .build();
        return new SSLConnectionSocketFactory(sslContext);
    }

    @Bean
    private static StatusComponent statusComponent() {
        return new StatusComponent();
    }

    @Bean
    private static UI ui(StatusComponent statusComponent,
                         InputComponent inputComponent,
                         OutputComponent outputComponent) {
        return new UI(statusComponent, inputComponent, outputComponent);
    }

}
