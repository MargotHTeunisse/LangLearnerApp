package nl.margothteunisse.langlearner.ui;

import nl.margothteunisse.langlearner.Card;
import nl.margothteunisse.langlearner.Deck;
import nl.margothteunisse.langlearner.Vocabulary;
import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="nl.margothteunisse.langlearner")
public class LangLearnerApp {
    public static void main(String[] args) throws DeckEmptyException {
        ApplicationContext apc = SpringApplication.run(LangLearnerApp.class);
        for (String s: apc.getBeanDefinitionNames()) {
            System.out.println(s);
        }

        Deck deck;
        try {
            deck = new Vocabulary("wordlist.txt").createDeck();
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


    @Bean
    String getVocabularyName() {
        return "wordlist.txt";
    }
}
