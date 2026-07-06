package nl.margothteunisse.langlearner.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Deck {
    private final Vocabulary vocab;
    private final List<Integer> cardsInDeck = new ArrayList<>();
    private Card drawnCard;

    public Deck(Vocabulary vocab) {
        this.vocab = vocab;
        cardsInDeck.addAll(vocab.getAllCardIDs());
    }

    public String readCard() {
        return drawnCard.read();
    }

    public boolean translateCard(String answer) {return drawnCard.check(answer);}

    public boolean draw() {
        if (remaining() == 0) {
            return false;
        }
        else {
            drawnCard = vocab.getCardByID(cardsInDeck.get(0));
            cardsInDeck.remove(0);
            return true;
        }
    }

    public int remaining() {
        return cardsInDeck.size();
    }
}
