package model;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.EmptyVocabulary;
import nl.margothteunisse.langlearner.model.TextVocabulary;
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
    public void testEmptyDeckContainsNoCards() {
        Deck deck = new Deck(new EmptyVocabulary());

        int deckSize = deck.remaining();

        Assertions.assertEquals(0, deckSize);
    }

    @Test
    public void testNonEmptyDeckContainsCards() throws IOException {
        Deck deck = new Deck(new TextVocabulary("cat.txt"));

        int deckSize = deck.remaining();

        Assertions.assertNotEquals(0, deckSize);
    }

    @Test
    public void testDeckSizeDecreasesByOneAfterDrawing() throws IOException {
        Deck deck = new Deck(new TextVocabulary("cat.txt"));
        int initialDeckSize = deck.remaining();

        deck.draw();

        Assertions.assertEquals(initialDeckSize-1, deck.remaining());
    }

    @Test
    public void testFrontOfSecondCardIsDifferentFromFirst() throws IOException {
        Deck deck = new Deck(new TextVocabulary("wordlist.txt"));
        deck.draw();
        String firstCard = deck.readCard();
        deck.draw();

        String secondCard = deck.readCard();

        Assertions.assertNotEquals(firstCard, secondCard);
    }
}
