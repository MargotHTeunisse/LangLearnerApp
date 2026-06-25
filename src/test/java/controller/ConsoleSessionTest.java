package controller;

import nl.margothteunisse.langlearner.controller.ConsoleSession;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.EmptyVocabulary;
import nl.margothteunisse.langlearner.view.FakeView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsoleSessionTest {
    @Test
    public void testSessionRunsForEmptyDeck() {
        Deck emptyDeck = new EmptyVocabulary().createDeck();

        Assertions.assertDoesNotThrow(() -> new ConsoleSession(emptyDeck, new FakeView()));
    }
}
