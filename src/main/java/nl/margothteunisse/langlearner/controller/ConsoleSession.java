package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.view.IView;

import java.util.Scanner;

public class ConsoleSession extends Session {
    public ConsoleSession(Deck deck, IView view) {
        super(deck, view);
        run();
    }

    private void run() {
        Scanner scn = new Scanner(System.in);
        String answer = "";
        while (deck.size() > 0) {
            updateView(answer);
            System.out.println(view.display().getBody());

            answer = scn.nextLine();
        }
        updateView(answer);
        System.out.println(view.display().getBody());
    }
}
