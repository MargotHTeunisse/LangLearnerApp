package model;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.vocabularies.TextVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class CardTest {
    @ParameterizedTest
    @CsvSource({"cat.txt, cat", "dog.txt, dog", "bird.txt, bird", "bear.txt, bear"})
    public void testCardFrontIsRead(String file, String front) throws IOException {
        Deck deck = new Deck(new TextVocabulary(file));
        deck.draw();

        String readText = deck.getDrawnCard().read();

        Assertions.assertEquals(front, readText);
    }

    @ParameterizedTest
    @CsvSource({"cat.txt, kissa", "dog.txt, koira", "bird.txt, lintu", "bear.txt, karhu"})
    public void testCheckReturnsTrueIfInputMatchesBack(String file, String back) throws IOException {
        Deck deck = new Deck(new TextVocabulary(file));
        deck.draw();

        boolean answerIsCorrect = deck.getDrawnCard().check(back);

        Assertions.assertTrue(answerIsCorrect);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "cat", "koira"})
    public void testCheckReturnsFalseIfInputDoesNotMatchBack(String input) throws IOException {
        Deck deck = new Deck(new TextVocabulary("cat.txt"));
        deck.draw();

        boolean answerIsCorrect = deck.getDrawnCard().check(input);

        Assertions.assertFalse(answerIsCorrect);
    }
}
