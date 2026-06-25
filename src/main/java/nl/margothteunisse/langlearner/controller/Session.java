package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import nl.margothteunisse.langlearner.view.IView;

public abstract class Session {
    Deck deck;
    private Card card;
    private final IView view;

    public Session(Deck deck, IView view) {
        this.deck = deck;
        this.view = view;
    }

    String getUpdatedView(String answer) {
        if (card != null) {
            if (card.check(answer)) {
                view.updateCorrect();
            } else {
                card.flip();
                view.updateIncorrect(card.read());
            }
        }

        try {
            card = deck.draw();
            view.updateCard(card.read());
        } catch (DeckEmptyException e) {
            view.close();
            card = null;
        }
        return view.display();
    }
}
