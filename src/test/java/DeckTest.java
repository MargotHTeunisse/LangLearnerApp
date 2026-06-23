import nl.margothteunisse.langlearner.Card;
import nl.margothteunisse.langlearner.Deck;
import nl.margothteunisse.langlearner.Vocabulary;
import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DeckTest {
    @Test
    public void testCannotDrawFromEmptyDeck() throws IOException {
        Deck deck = new Vocabulary("empty.txt").createDeck();

        Assertions.assertThrows(DeckEmptyException.class, deck::draw);
    }

    @Test
    public void testCanDrawFromNonEmptyDeck() throws IOException {
        Deck deck = new Vocabulary("cat.txt").createDeck();

        Assertions.assertDoesNotThrow(deck::draw);
    }

    @Test
    public void testEmptyDeckContainsNoCards() throws IOException {
        Deck deck = new Vocabulary("empty.txt").createDeck();

        int deckSize = deck.size();

        Assertions.assertEquals(0, deckSize);
    }

    @Test
    public void testNonEmptyDeckContainsCards() throws IOException {
        Deck deck = new Vocabulary("cat.txt").createDeck();

        int deckSize = deck.size();

        Assertions.assertNotEquals(0, deckSize);
    }

    @Test
    public void testDeckSizeDecreasesByOneAfterDrawing() throws DeckEmptyException, IOException {
        Deck deck = new Vocabulary("cat.txt").createDeck();
        int initialDeckSize = deck.size();

        Card card = deck.draw();

        Assertions.assertEquals(initialDeckSize-1, deck.size());
    }

    @Test
    public void testFrontOfSecondCardIsDifferentFromFirst() throws IOException, DeckEmptyException {
        Deck deck = new Vocabulary("wordlist.txt").createDeck();
        Card firstCard = deck.draw();

        Card secondCard = deck.draw();

        Assertions.assertNotEquals(firstCard.read(), secondCard.read());
    }
}
