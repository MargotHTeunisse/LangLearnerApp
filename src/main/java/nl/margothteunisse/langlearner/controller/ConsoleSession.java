package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Deck;

import java.util.Scanner;

public class ConsoleSession extends Session implements AutoCloseable{
    public ConsoleSession(Deck deck) {
        super(deck);
    }

    public void run() {
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
    }
}
