package nl.margothteunisse.langlearner.model.vocabularies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
@Profile("text")
@Qualifier("internal")
public class InternalTextVocabulary extends TextVocabulary{
    private String sourceLanguage;
    private String targetLanguage;

    public InternalTextVocabulary(@Value("${vocabulary.filename}") String filename) throws IOException {
        super(filename);
    }

    @Override
    List<String> readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        String[] languages = reader.readLine().split("\\s*,\\s*");
        sourceLanguage = languages[0];
        targetLanguage = languages[1];
        return reader.lines().toList();
    }

    @Override
    public List<Integer> getAllCardIDsForLanguages(String sourceLanguage, String targetLanguage) {
        if (sourceLanguage.equals(this.sourceLanguage) &&
                targetLanguage.equals(this.targetLanguage)) {
            return getAllCardIDs();
        }

        if (sourceLanguage.equals(this.targetLanguage) &&
        targetLanguage.equals(this.sourceLanguage)) {
            return getAllFlippedCardIDs();
        }

        return List.of();
    }
}
