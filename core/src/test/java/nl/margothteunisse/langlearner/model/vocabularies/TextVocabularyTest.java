package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;

public class TextVocabularyTest {
    @ParameterizedTest
    @CsvSource({"0, empty.txt", "1, cat.txt", "1, dog.txt", "4, wordlist.txt"})
    public void testNumberOfIndicesMatchesLines(int length, String filename) throws IOException {
        IVocabulary vocabulary = new InternalTextVocabulary(filename);

        List<Integer> cardIDs = vocabulary.getAllCardIDs();

        Assertions.assertEquals(length, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"0, cat", "1, dog", "2, bird", "3, bear"})
    public void testCardFrontAtIndexIsCorrect(int index, String front) throws IOException {
        IVocabulary vocabulary = new InternalTextVocabulary("wordlist.txt");

        Card card = vocabulary.getCardByID(index);

        Assertions.assertEquals(front, card.read());
    }

    @ParameterizedTest
    @CsvSource({"0, kissa", "1, koira", "2, lintu", "3, karhu"})
    public void testCardBackAtIndexIsCorrect(int index, String back) throws CardFlippedException, IOException {
        IVocabulary vocabulary = new InternalTextVocabulary("wordlist.txt");

        Card card = vocabulary.getCardByID(index);

        Assertions.assertTrue(card.check(back));
    }

    @Test
    public void testNoCardsRetrievedIfLanguageIsUnavailable() throws IOException {
        IVocabulary vocabulary = new InternalTextVocabulary("wordlist.txt");

        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages("", "");

        Assertions.assertEquals(0, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"wordlist.txt, EN, FI, 4", "cat.txt, EN, FI, 1", "gato.txt, ES, FR, 1"})
    public void testCardsRetrievedIfLanguageMatches(String filename, String sourceLanguage,
                                                    String targetLanguage, int size) throws IOException {
        IVocabulary vocabulary = new InternalTextVocabulary(filename);

        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages(sourceLanguage, targetLanguage);

        Assertions.assertEquals(size, cardIDs.size());
    }
}
