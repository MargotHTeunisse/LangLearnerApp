package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TextVocabularyTest {
    @ParameterizedTest
    @CsvSource({"0, empty.txt", "1, cat.txt", "1, dog.txt", "4, wordlist.txt"})
    public void testNumberOfIndicesMatchesLines(int length, String filename) throws IOException {
        URL url = TextVocabulary.class.getResource("/"+filename);
        IVocabulary vocabulary = new TextVocabulary(url);

        List<Integer> cardIDs = vocabulary.getAllCardIDs();

        Assertions.assertEquals(length, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"0, cat", "1, dog", "2, bird", "3, bear"})
    public void testCardFrontAtIndexIsCorrect(int index, String front) throws IOException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        Card card = vocabulary.getCardByID(index);

        Assertions.assertEquals(front, card.read());
    }

    @ParameterizedTest
    @CsvSource({"0, kissa", "1, koira", "2, lintu", "3, karhu"})
    public void testCardBackAtIndexIsCorrect(int index, String back) throws CardFlippedException, IOException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        Card card = vocabulary.getCardByID(index);

        Assertions.assertTrue(card.check(back));
    }

    @Test
    public void testNoCardsRetrievedIfLanguageIsUnavailable() throws IOException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages("", "");

        Assertions.assertEquals(0, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"wordlist.txt, EN, FI, 4", "cat.txt, EN, FI, 1", "gato.txt, ES, FR, 1"})
    public void testCardsRetrievedIfLanguageMatches(String filename, String sourceLanguage,
                                                    String targetLanguage, int size) throws IOException {
        URL url = TextVocabulary.class.getResource("/"+filename);
        IVocabulary vocabulary = new TextVocabulary(url);

        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages(sourceLanguage, targetLanguage);

        Assertions.assertEquals(size, cardIDs.size());
    }

    @Test
    public void testCardsRetrievedForFlippedTranslationDirection() throws IOException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages("FI", "EN");

        Assertions.assertEquals(4, cardIDs.size());
    }

    @Test
    public void testFlippedCardsUseSecondSetOfIndices() throws IOException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages("FI", "EN");

        Assertions.assertEquals(List.of(4, 5, 6, 7), cardIDs);
    }

    @ParameterizedTest
    @CsvSource({"0, kissa", "1, koira", "2, lintu", "3, karhu"})
    public void testFlippedCardFrontAtIndexIsCorrect(int index, String front) throws IOException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        Card card = vocabulary.getCardByID(index+4);

        Assertions.assertEquals(front, card.read());
    }

    @ParameterizedTest
    @CsvSource({"0, cat", "1, dog", "2, bird", "3, bear"})
    public void testFlippedCardBackAtIndexIsCorrect(int index, String back)
            throws IOException, CardFlippedException {
        URL url = TextVocabulary.class.getResource("/wordlist.txt");
        IVocabulary vocabulary = new TextVocabulary(url);

        Card card = vocabulary.getCardByID(index+4);

        Assertions.assertTrue(card.check(back));
    }
}
