package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

import java.io.IOException;

public class Deck {
    private final Vocabulary vocab;
    private int size;

    public Deck(String dataFile) throws IOException {
        vocab = new Vocabulary(dataFile);
        size = vocab.wordCount();
    }
    
    
    public Card draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        Card card = vocab.getCard(size-1);

        size--;
        return card;
    }

    public int size() {
        return size;
    }
}
