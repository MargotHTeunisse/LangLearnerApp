package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;
import org.springframework.stereotype.Component;

@Component
public class Deck {
    private final TextVocabulary vocab;
    private int size;

    Deck(TextVocabulary vocab) {
        this.vocab = vocab;
        size = vocab.wordCount();
    }
    
    
    public Card draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        Card card = vocab.getCardByID(size-1);

        size--;
        return card;
    }

    public int size() {
        return size;
    }
}
