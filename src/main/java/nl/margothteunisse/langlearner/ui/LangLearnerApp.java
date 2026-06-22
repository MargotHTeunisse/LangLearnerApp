package nl.margothteunisse.langlearner.ui;

import nl.margothteunisse.langlearner.Card;
import nl.margothteunisse.langlearner.Deck;
import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

import java.io.IOException;
import java.util.Scanner;

public class LangLearnerApp {
    public static void main(String[] args) throws DeckEmptyException {
        Deck deck;
        try {
            deck = new Deck("wordlist.txt");
        }
        catch (IOException e) {
            System.out.println("Could not find file '{}'.");
            return;
        }

        Scanner scn = new Scanner(System.in);
        while (deck.size() > 0) {
            Card card = deck.draw();
            System.out.println("Translate: " + card.read());
            if (card.check(scn.nextLine())) {
                System.out.println("Correct!");
            }
            else {
                card.flip();
                System.out.println("That is incorrect. The correct answer is " + card.read() + ".");
            }
        }
    }
}
