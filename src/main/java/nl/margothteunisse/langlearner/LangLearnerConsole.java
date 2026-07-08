package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.TextVocabulary;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.Scanner;


public class LangLearnerConsole {
    public static void main(String @NonNull [] args) {
        try  {
            Deck deck = new Deck(new TextVocabulary(args[0]));

            Scanner scn = new Scanner(System.in);
            while (deck.draw()) {
                System.out.println("Translate: " + deck.readCard());
                String answer = scn.nextLine();
                if (deck.translateCard(answer)) {
                    System.out.println("Correct!");
                }
                else {
                    System.out.println("That is incorrect.");
                }
            }
            System.out.println("You have translated all words!");
        } catch (IOException e) {
            System.out.println("Could not find file.");
        }
    }
}


