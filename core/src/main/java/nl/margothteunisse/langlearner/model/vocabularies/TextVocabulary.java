package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TextVocabulary implements IVocabulary {
    private final String[][] words;

    public TextVocabulary(@Value("${vocabulary.filename}") String filename) throws IOException {
        List<String> lines = readFile(filename);
        words = new String[lines.size()][2];
        int wordIndex = 0;
        for (String line: lines) {
            String[] wordPair = line.split("\\s*,\\s*");
            words[wordIndex] = wordPair;
            wordIndex++;
        }
    }

    abstract List<String> readFile(String filename) throws IOException;

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
