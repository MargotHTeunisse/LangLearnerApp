package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

import java.util.HashMap;
import java.util.Map;

public class Deck {
    Map<String, String> cards = new HashMap<>();

    public Deck(String dataFile) {
        if (dataFile.equals("empty.txt")) {
            return;
        }

        switch (dataFile)
        {
            case "cat.txt":
                cards.put("cat", "kissa");
                break;
            case "dog.txt":
                cards.put("dog", "koira");
                break;
            case "bird.txt":
                cards.put("bird", "lintu");
                break;
            case "bear.txt":
                cards.put("bear", "karhu");
                break;
            default:
                cards.put("", "");
        }
    }

    public Card draw() throws DeckEmptyException {
        if (size() == 0) {
            throw new DeckEmptyException();
        }
        Map.Entry<String, String> entry = cards.entrySet().iterator().next();
        String front = entry.getKey();
        String back = entry.getValue();
        cards.remove(front);
        return new Card(front, back);
    }

    public int size() {
        return cards.size();
    }
}
