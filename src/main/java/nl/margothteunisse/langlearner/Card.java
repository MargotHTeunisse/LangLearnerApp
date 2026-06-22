package nl.margothteunisse.langlearner;

public class Card {
    private final String front;
    private final String back;
    private boolean flipped = false;

    Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String read() {
        return flipped? back:front;
    }

    public boolean check(String input) {
        return (flipped? input.equals(front):input.equals(back));
    }

    public void flip() {
        flipped = !flipped;
    }
}
