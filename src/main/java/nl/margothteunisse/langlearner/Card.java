package nl.margothteunisse.langlearner;

public class Card {
    private final String front;
    private final String back;

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String read() {
        return front;
    }

    public boolean check(String input) {
        return (input == back);
    }
}
