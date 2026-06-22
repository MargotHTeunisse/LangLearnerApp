import nl.margothteunisse.langlearner.Card;
import nl.margothteunisse.langlearner.Deck;
import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DeckTest {
    @Test
    public void testCannotDrawFromEmptyDeck() throws IOException {
        Deck deck = new Deck("empty.txt");

        Assertions.assertThrows(DeckEmptyException.class, deck::draw);
    }

    @Test
    public void testCanDrawFromNonEmptyDeck() throws IOException {
        Deck deck = new Deck("cat.txt");

        Assertions.assertDoesNotThrow(deck::draw);
    }

    @Test
    public void testEmptyDeckContainsNoCards() throws IOException {
        Deck deck = new Deck("empty.txt");

        int deckSize = deck.size();

        Assertions.assertEquals(0, deckSize);
    }

    @Test
    public void testNonEmptyDeckContainsCards() throws IOException {
        Deck deck = new Deck("cat.txt");

        int deckSize = deck.size();

        Assertions.assertNotEquals(0, deckSize);
    }

    @Test
    public void testDeckSizeDecreasesByOneAfterDrawing() throws DeckEmptyException, IOException {
        Deck deck = new Deck("cat.txt");
        int initialDeckSize = deck.size();

        Card card = deck.draw();

        Assertions.assertEquals(initialDeckSize-1, deck.size());
    }
}
