package controller;

import nl.margothteunisse.langlearner.controller.LearningSession;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.EmptyVocabulary;
import nl.margothteunisse.langlearner.view.FakeInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LearningSessionTest {
    @Test
    public void testSessionRunsForEmptyDeck() {
        Deck emptyDeck = new EmptyVocabulary().createDeck();

        Assertions.assertDoesNotThrow(() -> new LearningSession(emptyDeck, new FakeInterface()));
    }
}
