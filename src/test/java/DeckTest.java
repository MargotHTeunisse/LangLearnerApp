import nl.margothteunisse.langlearner.Deck;
import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    public void testCannotDrawFromEmptyDeck() {
        Deck deck = new Deck("empty.txt");

        Assertions.assertThrows(DeckEmptyException.class, deck::draw);
    }
}
