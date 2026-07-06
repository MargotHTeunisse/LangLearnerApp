package nl.margothteunisse.langlearner.model;

class Card {
    private final String front;
    private final String back;
    private boolean flipped = false;

    Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    String read() {
        return flipped? back:front;
    }

    boolean check(String input) {
        return (flipped? input.equals(front):input.equals(back));
    }

    void flip() {
        flipped = !flipped;
    }
}
