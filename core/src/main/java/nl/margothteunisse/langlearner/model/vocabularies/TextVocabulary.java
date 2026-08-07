package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TextVocabulary implements IVocabulary {
    private final String[][] words;

    public TextVocabulary(@Value("${vocabulary.filename}") String filename)
             {
        List<String> lines = readFile(filename);
        words = new String[lines.size()][2];
        int wordIndex = 0;
        for (String line: lines) {
            String[] wordPair = line.split("\\s*,\\s*");
            words[wordIndex] = wordPair;
            wordIndex++;
        }
    }

    abstract List<String> readFile(String filename);

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
