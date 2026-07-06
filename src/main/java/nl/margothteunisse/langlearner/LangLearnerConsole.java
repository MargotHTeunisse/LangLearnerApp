package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.controller.ConsoleSession;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.TextVocabulary;
import org.jspecify.annotations.NonNull;

import java.io.IOException;

public class LangLearnerConsole {
    public static void main(String @NonNull [] args) {
        try (ConsoleSession session = new ConsoleSession(new Deck(new TextVocabulary(args[0])))) {
            session.run();
        } catch (IOException e) {
            System.out.println("Could not find file.");
        }
    }
}
