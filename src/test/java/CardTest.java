import nl.margothteunisse.langlearner.Card;
import nl.margothteunisse.langlearner.Deck;
import nl.margothteunisse.langlearner.exceptions.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class CardTest {
    @ParameterizedTest
    @CsvSource({"cat.txt, cat", "dog.txt, dog", "bird.txt, bird", "bear.txt, bear"})
    public void testCardFrontIsRead(String file, String front) throws DeckEmptyException, IOException {
        Deck deck = new Deck(file);
        Card card = deck.draw();

        String readText = card.read();

        Assertions.assertEquals(front, readText);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, kissa", "dog.txt, koira", "bird.txt, lintu", "bear.txt, karhu"})
    public void testCardBackIsReadAfterFlipping(String file, String back) throws DeckEmptyException, IOException {
        Deck deck = new Deck(file);
        Card card = deck.draw();
        card.flip();

        String readText = card.read();

        Assertions.assertEquals(back, readText);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, kissa", "dog.txt, koira", "bird.txt, lintu", "bear.txt, karhu"})
    public void testCheckReturnsTrueIfInputMatchesBack(String file, String back) throws DeckEmptyException, IOException {
        Deck deck = new Deck(file);
        Card card = deck.draw();

        boolean answerIsCorrect = card.check(back);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, cat", "dog.txt, dog", "bird.txt, bird", "bear.txt, bear"})
    public void testCheckReturnsTrueIfInputMatchesFrontAfterFlipping(String file, String front) throws DeckEmptyException, IOException {
        Deck deck = new Deck(file);
        Card card = deck.draw();
        card.flip();

        boolean answerIsCorrect = card.check(front);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "cat", "koira"})
    public void testCheckReturnsFalseIfInputDoesNotMatchBack(String input) throws DeckEmptyException, IOException {
        Deck deck = new Deck("cat.txt");
        Card card = deck.draw();

        boolean answerIsCorrect = card.check(input);

        Assertions.assertFalse(answerIsCorrect);
    }
}
