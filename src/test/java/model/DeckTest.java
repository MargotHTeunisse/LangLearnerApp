package model;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.EmptyVocabulary;
import nl.margothteunisse.langlearner.model.TextVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DeckTest {
    @Test
    public void testCannotDrawFromEmptyDeck() {
        Deck deck = new EmptyVocabulary().createDeck();

        Assertions.assertThrows(DeckEmptyException.class, deck::draw);
    }

    @Test
    public void testCanDrawFromNonEmptyDeck() throws IOException {
        Deck deck = new TextVocabulary("cat.txt").createDeck();

        Assertions.assertDoesNotThrow(deck::draw);
    }

    @Test
    public void testEmptyDeckContainsNoCards() throws IOException {
        Deck deck = new EmptyVocabulary().createDeck();

        int deckSize = deck.size();

        Assertions.assertEquals(0, deckSize);
    }

    @Test
    public void testNonEmptyDeckContainsCards() throws IOException {
        Deck deck = new TextVocabulary("cat.txt").createDeck();

        int deckSize = deck.size();

        Assertions.assertNotEquals(0, deckSize);
    }

    @Test
    public void testDeckSizeDecreasesByOneAfterDrawing() throws DeckEmptyException, IOException {
        Deck deck = new TextVocabulary("cat.txt").createDeck();
        int initialDeckSize = deck.size();

        deck.draw();

        Assertions.assertEquals(initialDeckSize-1, deck.size());
    }

    @Test
    public void testFrontOfSecondCardIsDifferentFromFirst() throws IOException, DeckEmptyException {
        Deck deck = new TextVocabulary("wordlist.txt").createDeck();
        deck.draw();
        Card firstCard = deck.getDrawnCard();
        deck.draw();

        Card secondCard = deck.getDrawnCard();

        Assertions.assertNotEquals(firstCard.read(), secondCard.read());
    }
}
