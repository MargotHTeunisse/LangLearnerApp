package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@Scope("prototype")
public class ConsoleSession implements CommandLineRunner {
    Deck deck;

    public ConsoleSession(Deck deck) {
        this.deck = deck;
    }

    public void run(String[] args) {
        Scanner scn = new Scanner(System.in);
        while (deck.draw()) {
            Card card = deck.getDrawnCard();
            System.out.println("Translate: " + card.read());
            String answer = scn.nextLine();
            if (card.check(answer)) {
                System.out.println("Correct!");
            }
            else {
                System.out.println("That is incorrect.");
            }
        }
        System.out.println("You have translated all words!");
    }
}
