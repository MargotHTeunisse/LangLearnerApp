package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

public class Deck {
    private final String dataFile;

    public Deck(String dataFile) {
        this.dataFile = dataFile;
    }

    public void draw() throws DeckEmptyException {
        if (dataFile.equals("empty.txt")) {
            throw new DeckEmptyException();
        }
    }
}
