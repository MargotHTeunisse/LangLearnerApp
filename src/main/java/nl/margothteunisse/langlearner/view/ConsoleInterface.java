package nl.margothteunisse.langlearner.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInterface implements UserInterface{
    private Scanner scn = new Scanner(System.in);

    public void showIncorrect(String correctWord) {
        System.out.println("That is incorrect. The correct answer is " + correctWord + ".");
    }

    public void showCorrect() {
        System.out.println("Correct!");
    }

    public String askForTranslation(String word) {
        System.out.println("Translate: " + word);
        return scn.nextLine();
    }
}
