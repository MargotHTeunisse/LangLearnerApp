package nl.margothteunisse.langlearner.config;

import nl.margothteunisse.langlearner.model.vocabularies.TextVocabulary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@Profile("text")
public class TextVocabularyConfig {
    @Bean
    URL internalTextVocabularyURL(@Value("${vocabulary.filename}") String filename) {
        return TextVocabulary.class.getResource("/"+filename);
    }

    @Bean
    @Profile("external")
    @Primary
    URL externalTextVocabularyURL(@Value("${vocabulary.filename}") String filename) throws MalformedURLException {
        return new File(filename).toURI().toURL();
    }
}
