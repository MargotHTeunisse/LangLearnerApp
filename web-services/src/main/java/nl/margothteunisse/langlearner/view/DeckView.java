package nl.margothteunisse.langlearner.view;

import nl.margothteunisse.langlearner.model.Deck;

public class DeckView {
    public String visibleWord;
    public boolean deckIsDepleted;
    public boolean answerIsVisible;

    public DeckView(String visibleWord, boolean deckIsDepleted, boolean answerIsVisible) {
        this.visibleWord = visibleWord;
        this.deckIsDepleted = deckIsDepleted;
        this.answerIsVisible = answerIsVisible;
    }
}
