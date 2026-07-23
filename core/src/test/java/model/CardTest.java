package model;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CardTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "cat", "dog"})
    public void testCardFrontIsRead(String front) {
        Card card = new Card(front, "");

        String readText = card.read();

        Assertions.assertEquals(front, readText);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "kissa", "koira"})
    public void testCheckReturnsTrueIfInputMatchesBack(String back) throws CardFlippedException {
        Card card = new Card("", back);

        boolean answerIsCorrect = card.check(back);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "cat", "koira"})
    public void testCheckReturnsFalseIfInputDoesNotMatchBack(String input) throws CardFlippedException {
        Card card = new Card("cat", "kissa");

        boolean answerIsCorrect = card.check(input);

        Assertions.assertFalse(answerIsCorrect);
    }

    @Test
    public void testCannotSubmitTranslationAfterFlipping() {
        Card card = new Card("", "");

        card.flip();

        Assertions.assertThrows(CardFlippedException.class, () -> card.check(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "kissa", "koira"})
    public void testCardBackIsReadAfterFlipping(String back){
        Card card = new Card("", back);

        card.flip();

        Assertions.assertEquals(back, card.read());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "kissa", "koira"})
    public void testFlippingTwiceHasNoEffect(String back){
        Card card = new Card("", back);

        card.flip();
        card.flip();

        Assertions.assertEquals(back, card.read());
    }

    @Test
    public void testCardCanBeFlippedOnce() {
        Card card = new Card("", "");

        boolean canFlip = card.flip();

        Assertions.assertTrue(canFlip);
    }

    @Test
    public void testCardCannotBeFlippedTwice(){
        Card card = new Card("", "");
        card.flip();

        boolean canFlip = card.flip();

        Assertions.assertFalse(canFlip);
    }
}
