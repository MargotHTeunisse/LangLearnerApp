package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.view.IView;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

//@Controller
public class ConsoleSession extends Session {
    public ConsoleSession(Deck deck, IView view) {
        super(deck, view);
        run();
    }

    private void run() {
        Scanner scn = new Scanner(System.in);
        String answer = "";
        while (deck.size() > 0) {
            System.out.println(getUpdatedView(answer));

            answer = scn.nextLine();
        }
        System.out.println(getUpdatedView(answer));
    }
}
