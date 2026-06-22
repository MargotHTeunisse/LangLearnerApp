package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

import java.util.HashMap;
import java.util.Map;

public class Deck {
    private final Vocabulary vocab;
    private int wordIndex;

    public Deck(String dataFile) {
        vocab = new Vocabulary(dataFile);
    }
    
    
    public Card draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        Card card = vocab.getCard(wordIndex);

        wordIndex++;
        return card;
    }

    public int size() {
        return vocab.wordCount() - wordIndex;
    }
}
