package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import nl.margothteunisse.langlearner.view.IView;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class ConsoleSession {
    private Deck deck;
    private IView view;

    public ConsoleSession(Deck deck, IView view) throws DeckEmptyException {
        this.deck = deck;
        this.view = view;
        run();
    }

    private void run() throws DeckEmptyException {
        Scanner scn = new Scanner(System.in);
        while (deck.size() > 0) {
            Card card = deck.draw();
            view.updateCard(card.read());
            System.out.println(view.display());

            String answer = scn.nextLine();
            if (card.check(answer)) {
                view.updateCorrect();
            }
            else {
                card.flip();
                view.updateIncorrect(card.read());
            }
        }
        view.close();
        System.out.println(view.display());
    }
}
