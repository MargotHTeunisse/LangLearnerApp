package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import nl.margothteunisse.langlearner.view.UserInterface;
import org.springframework.stereotype.Controller;

@Controller
public class LearningSession {
    private Deck deck;
    private UserInterface ui;

    public LearningSession (Deck deck, UserInterface ui) throws DeckEmptyException {
        this.deck = deck;
        this.ui = ui;
        run();
    }

    private void run() throws DeckEmptyException {
        while (deck.size() > 0) {
            Card card = deck.draw();
            String answer = ui.askForTranslation(card.read());
            if (card.check(answer)) {
                ui.showCorrect();
            }
            else {
                card.flip();
                ui.showIncorrect(card.read());
            }
        }
    }
}
