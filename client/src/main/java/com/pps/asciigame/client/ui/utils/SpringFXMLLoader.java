package com.pps.asciigame.client.ui.utils;

import com.pps.asciigame.client.Main;
import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class SpringFXMLLoader {
    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);

    public Object load(final String url) throws IOException {
        try (final var fxmlInputStream = getClass().getResourceAsStream(url)) {
            final var fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            return fxmlLoader.load(fxmlInputStream);
        }
    }
}
