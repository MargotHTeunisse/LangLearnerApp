package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import nl.margothteunisse.langlearner.view.IView;
import org.springframework.http.ResponseEntity;

public abstract class Session {
    Deck deck;
    private Card card;
    final IView view;

    public Session(Deck deck, IView view) {
        this.deck = deck;
        this.view = view;
    }

    void updateView(String answer) {
        if (card != null) {
            if (card.check(answer)) {
                view.updateCorrect();
            } else {
                card.flip();
                view.updateIncorrect(card.read());
            }
        }

        try {
            deck.draw();
            card = deck.getDrawnCard();
            view.updateCard(card.read());
        } catch (DeckEmptyException e) {
            view.close();
            card = null;
        }
    }
}
