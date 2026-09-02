package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("text")
public class TextVocabulary implements IVocabulary {
    private String sourceLanguage;
    private String targetLanguage;
    private final String[][] words;

    @Autowired
    public TextVocabulary(URL url) throws IOException {
        List<String> lines = readFile(url);
        words = new String[lines.size()][2];
        int wordIndex = 0;
        for (String line: lines) {
            String[] wordPair = line.split("\\s*,\\s*");
            words[wordIndex] = wordPair;
            wordIndex++;
        }
    }

    List<String> readFile(URL url) throws IOException {
        InputStream inputStream = url.openStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        String[] languages = reader.readLine().split("\\s*,\\s*");
        sourceLanguage = languages[0];
        targetLanguage = languages[1];
        return reader.lines().toList();
    }

    @Override
    public Card getCardByID(int wordID) {
        if (wordID >= words.length) {
            return new Card(words[wordID-words.length][1], words[wordID-words.length][0]);
        }

        return new Card(words[wordID][0], words[wordID][1]);
    }

    protected List<Integer> getAllFlippedCardIDs() {
        List<Integer> cardIDs = new ArrayList<>();
        for (int i = words.length; i < 2*words.length; i++) {
            cardIDs.add(i);
        }
        return cardIDs;
    }

    @Override
    public List<Integer> getAllCardIDs() {
        List<Integer> cardIDs = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            cardIDs.add(i);
        }
        return cardIDs;
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

    @Override
    public List<String> getAllLanguages() {
        return List.of(sourceLanguage, targetLanguage);
    }

    @Override
    public List<String> getTargetLanguagesForSource(String sourceLanguage) {
        if (sourceLanguage.equals(this.sourceLanguage)) {
            return List.of(this.targetLanguage);
        }

        if (sourceLanguage.equals(this.targetLanguage)) {
            return List.of(this.sourceLanguage);
        }

        return List.of();
    }
}
