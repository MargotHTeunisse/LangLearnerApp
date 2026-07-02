package nl.margothteunisse.langlearner.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TextVocabulary extends Vocabulary {
    private final String[][] words;

    public TextVocabulary(@Value("${vocabulary.filename}") String filename)
            throws IOException {
        List<String> lines = readFile(filename);
        words = new String[lines.size()][2];
        int wordIndex = 0;
        for (String line: lines) {
            String[] wordPair = line.split("\\s*,\\s*");
            words[wordIndex] = wordPair;
            wordIndex++;
        }
    }

    private static List<String> readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        return reader.lines().toList();
    }

    public Card getCardByID(int wordID) {
        return new Card(words[wordID][0], words[wordID][1]);
    }

    @Override
    public List<Integer> getAllCardIDs() {
        List<Integer> cardIDs = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            cardIDs.add(i);
        }
        return cardIDs;
    }
}
