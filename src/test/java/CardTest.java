import nl.margothteunisse.langlearner.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CardTest {
    @ParameterizedTest
    @ValueSource(strings = {"cat", "dog", "bird", "bear"})
    public void testCardFrontIsRead(String front) {
        Card card = new Card(front, "");

        String readText = card.read();

        Assertions.assertEquals(front, readText);
    }

    @ParameterizedTest
    @ValueSource(strings = {"kissa", "koira", "lintu", "karhu"})
    public void testCheckReturnsTrueIfInputMatchesBack(String back) {
        Card card = new Card("", back);

        boolean answerIsCorrect = card.check(back);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "cat", "koira"})
    public void testCheckReturnsFalseIfInputDoesNotMatchBack(String input) {
        Card card = new Card("cat", "kissa");

        boolean answerIsCorrect = card.check(input);

        Assertions.assertFalse(answerIsCorrect);
    }
}
