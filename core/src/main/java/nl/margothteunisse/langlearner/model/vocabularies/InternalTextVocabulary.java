package nl.margothteunisse.langlearner.model.vocabularies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
@Profile("text")
@Qualifier("internal")
public class InternalTextVocabulary extends TextVocabulary{
    public InternalTextVocabulary(String filename) {
        super(filename);
    }

    @Override
    List<String> readFile(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        return reader.lines().toList();
    }
}
