package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class Deck {
    private final Vocabulary vocab;
    private final List<Integer> cardsInDeck = new ArrayList<>();

    Deck(Vocabulary vocab) {
        this.vocab = vocab;
        cardsInDeck.addAll(vocab.getAllCardIDs());
    }
    
    
    public Card draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        Card card = vocab.getCardByID(cardsInDeck.get(0));
        cardsInDeck.remove(0);
        return card;
    }

    public int size() {
        return cardsInDeck.size();
    }
}
