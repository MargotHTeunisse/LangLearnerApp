package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

public class Deck {
    private final Vocabulary vocab;
    private int size;

    public Deck(String dataFile) {
        vocab = new Vocabulary(dataFile);
        size = vocab.wordCount();
    }
    
    
    public Card draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        Card card = vocab.getCard(0);

        size--;
        return card;
    }

    public int size() {
        return size;
    }
}
