import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.TextVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class CardTest {
    @ParameterizedTest
    @CsvSource({"cat.txt, cat", "dog.txt, dog", "bird.txt, bird", "bear.txt, bear"})
    public void testCardFrontIsRead(String file, String front) throws DeckEmptyException, IOException {
        Card card = new TextVocabulary(file).createDeck().draw();

        String readText = card.read();

        Assertions.assertEquals(front, readText);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, kissa", "dog.txt, koira", "bird.txt, lintu", "bear.txt, karhu"})
    public void testCardBackIsReadAfterFlipping(String file, String back) throws DeckEmptyException, IOException {
        Card card = new TextVocabulary(file).createDeck().draw();

        card.flip();

        String readText = card.read();

        Assertions.assertEquals(back, readText);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, kissa", "dog.txt, koira", "bird.txt, lintu", "bear.txt, karhu"})
    public void testCheckReturnsTrueIfInputMatchesBack(String file, String back) throws DeckEmptyException, IOException {
        Card card = new TextVocabulary(file).createDeck().draw();

        boolean answerIsCorrect = card.check(back);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, cat", "dog.txt, dog", "bird.txt, bird", "bear.txt, bear"})
    public void testCheckReturnsTrueIfInputMatchesFrontAfterFlipping(String file, String front) throws DeckEmptyException, IOException {
        Card card = new TextVocabulary(file).createDeck().draw();
        card.flip();

        boolean answerIsCorrect = card.check(front);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "cat", "koira"})
    public void testCheckReturnsFalseIfInputDoesNotMatchBack(String input) throws DeckEmptyException, IOException {
        Card card = new TextVocabulary("cat.txt").createDeck().draw();

        boolean answerIsCorrect = card.check(input);

        Assertions.assertFalse(answerIsCorrect);
    }
}
