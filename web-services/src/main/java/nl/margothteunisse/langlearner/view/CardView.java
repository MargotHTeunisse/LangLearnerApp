package nl.margothteunisse.langlearner.view;

public class CardView {
    public final String visibleWord;
    public final Boolean answerIsCorrect;

    public CardView(String visibleWord) {
        this.visibleWord = visibleWord;
        answerIsCorrect = null;
    }

    public CardView(String visibleWord, boolean answerIsCorrect) {
        this.visibleWord = visibleWord;
        this.answerIsCorrect = answerIsCorrect;
    }
}
