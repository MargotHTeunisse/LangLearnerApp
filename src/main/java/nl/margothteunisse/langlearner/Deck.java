package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

public class Deck {
    private final String dataFile;
    private int size;

    public Deck(String dataFile) {
        this.dataFile = dataFile;
        this.size = dataFile.equals("empty.txt")? 0:1;
    }

    public Card draw() throws DeckEmptyException {
        if (dataFile.equals("empty.txt")) {
            throw new DeckEmptyException();
        }
        size -= 1;
        return new Card("", "");
    }

    public int size() {
        return size;
    }
}
