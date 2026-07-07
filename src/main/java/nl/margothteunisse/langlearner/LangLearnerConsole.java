package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

import java.util.Scanner;

@SpringBootApplication
@Profile("console")
public class LangLearnerConsole  implements CommandLineRunner{
    private final Deck deck;

    public LangLearnerConsole(Deck deck) {
        this.deck = deck;
    }

    public static void main(String @NonNull [] args) {
        ApplicationContext context = SpringApplication.run(LangLearnerConsole.class, args);
        SpringApplication.exit(context);
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
