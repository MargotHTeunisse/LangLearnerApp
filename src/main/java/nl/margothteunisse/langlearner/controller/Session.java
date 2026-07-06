package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Deck;

public abstract class Session implements AutoCloseable {
    Deck deck;

    public Session(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void close()  {
    }
}
