package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Deck {
    private final Vocabulary vocab;
    private final List<Integer> cardsInDeck = new ArrayList<>();
    private Card drawnCard;

    Deck(Vocabulary vocab) {
        this.vocab = vocab;
        cardsInDeck.addAll(vocab.getAllCardIDs());
    }

    public Card getDrawnCard() {
        return drawnCard;
    }

    public void draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        drawnCard = vocab.getCardByID(cardsInDeck.get(0));
        cardsInDeck.remove(0);
    }

    public int size() {
        return cardsInDeck.size();
    }
}
