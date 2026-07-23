package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;

public class Card {
    private final String front;
    private final String back;
    private boolean flipped = false;

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String read() {
        if (flipped) {
            return back;
        }
        else {
            return front;
        }
    }

    public boolean check(String input) throws CardFlippedException {
        if (flipped) {
            throw new CardFlippedException("Cannot submit answer because answer is visible to user.");
        }
        return input.equals(back);
    }

    public boolean flip() {
        if (flipped) {
            return false;
        }
        flipped = true;
        return true;
    }

    public boolean getFlipped() {
        return flipped;
    }
}
