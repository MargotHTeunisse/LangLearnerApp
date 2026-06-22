package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

public class Deck {
    public Deck(String file) {
    }

    public void draw() throws DeckEmptyException {
        throw new DeckEmptyException();
    }
}
