package model;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.vocabularies.EmptyVocabulary;
import nl.margothteunisse.langlearner.model.vocabularies.TextVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DeckTest {
    @Test
    public void testCannotDrawFromEmptyDeck() {
        Deck deck = new Deck(new EmptyVocabulary());

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @Test
    public void testCanDrawFromNonEmptyDeck() throws IOException {
        Deck deck = new Deck(new TextVocabulary("cat.txt"));

        boolean canDraw = deck.draw();

        Assertions.assertTrue(canDraw);
    }

    @Test
    public void testCardIsRemovedAfterDrawing() throws IOException {
        Deck deck = new Deck(new TextVocabulary("cat.txt"));
        deck.draw();

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @Test
    public void testFrontOfSecondCardIsDifferentFromFirst() throws IOException {
        Deck deck = new Deck(new TextVocabulary("wordlist.txt"));
        deck.draw();
        String firstCard = deck.getDrawnCard().read();
        deck.draw();

        String secondCard = deck.getDrawnCard().read();

        Assertions.assertNotEquals(firstCard, secondCard);
    }
}
