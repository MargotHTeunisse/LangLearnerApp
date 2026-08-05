package nl.margothteunisse.langlearner.view;

public class DeckView {
    public final CardView card;
    public final boolean deckIsDepleted;

    public DeckView(String visibleWord, boolean deckIsDepleted) {
        this.card = new CardView(visibleWord);
        this.deckIsDepleted = deckIsDepleted;
    }
}
