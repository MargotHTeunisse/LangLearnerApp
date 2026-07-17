package nl.margothteunisse.langlearner.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Deck {
    private final IVocabulary vocabulary;
    private final List<Integer> cardsInDeck = new ArrayList<>();
    private Card drawnCard;

    public Deck(IVocabulary vocabulary) {
        this.vocabulary = vocabulary;
        cardsInDeck.addAll(vocabulary.getAllCardIDs());
    }

    public Card getDrawnCard() {return drawnCard;}

    public boolean draw() {
        if (cardsInDeck.isEmpty()) {
            return false;
        }
        else {
            drawnCard = vocabulary.getCardByID(cardsInDeck.get(0));
            cardsInDeck.remove(0);
            return true;
        }
    }
}
