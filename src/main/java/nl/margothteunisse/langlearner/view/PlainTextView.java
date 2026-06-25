package nl.margothteunisse.langlearner.view;

import org.springframework.stereotype.Component;

@Component
public class PlainTextView implements IView {

    private String feedbackMessage = "";
    private String cardFront;
    private boolean deckIsEmpty = false;

    @Override
    public String display() {
        String message = feedbackMessage;
        if (deckIsEmpty) {
            message += "You have finished translating all words!";
        }
        else {
            message += "Translate: " + cardFront;
        }
        return message;
    }

    @Override
    public void close() {
        deckIsEmpty = true;
    }

    @Override
    public void updateCard(String cardFront) {
        this.cardFront = cardFront;
    }

    @Override
    public void updateCorrect() {
        this.feedbackMessage = "Correct!\n";
    }

    @Override
    public void updateIncorrect(String cardBack) {
        this.feedbackMessage = "That is incorrect. The correct word is " + cardBack + ".\n";
    }
}
