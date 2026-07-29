package nl.margothteunisse.langlearner.view;

public class CardView {
    public final String visibleWord;
    public final boolean answerIsVisible;

    public CardView(String visibleWord, boolean answerIsVisible) {
        this.visibleWord = visibleWord;
        this.answerIsVisible = answerIsVisible;
    }
}
