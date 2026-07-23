package model;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.vocabularies.TextVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class TextVocabularyTest {
    @ParameterizedTest
    @CsvSource({"0, empty.txt", "1, cat.txt", "1, dog.txt", "4, wordlist.txt"})
    public void testNumberOfIndicesMatchesLines(int length, String filename){
        IVocabulary vocabulary = new TextVocabulary(filename);

        List<Integer> cardIDs = vocabulary.getAllCardIDs();

        Assertions.assertEquals(length, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"0, cat", "1, dog", "2, bird", "3, bear"})
    public void testCardFrontAtIndexIsCorrect(int index, String front) {
        IVocabulary vocabulary = new TextVocabulary("wordlist.txt");

        Card card = vocabulary.getCardByID(index);

        Assertions.assertEquals(front, card.read());
    }

    @ParameterizedTest
    @CsvSource({"0, kissa", "1, koira", "2, lintu", "3, karhu"})
    public void testCardBackAtIndexIsCorrect(int index, String back) {
        IVocabulary vocabulary = new TextVocabulary("wordlist.txt");

        Card card = vocabulary.getCardByID(index);

        Assertions.assertTrue(card.check(back));
    }
}
