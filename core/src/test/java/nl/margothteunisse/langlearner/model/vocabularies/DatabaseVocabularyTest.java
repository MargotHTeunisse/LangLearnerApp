package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest(classes=DatabaseVocabulary.class)
@EnableAutoConfiguration
@ActiveProfiles(profiles="database")
@TestPropertySource(locations="classpath:test.properties")
public class DatabaseVocabularyTest {
    @Autowired
    IVocabulary vocabulary;

    @Test
    public void testNumberOfIndicesMatchesLines() {
        List<Integer> cardIDs = vocabulary.getAllCardIDs();

        Assertions.assertEquals(7, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"1, cat", "2, dog", "3, bird", "4, bear"})
    public void testCardFrontAtIndexIsCorrect(int index, String front) {
        Card card = vocabulary.getCardByID(index);

        Assertions.assertEquals(front, card.read());
    }

    @ParameterizedTest
    @CsvSource({"1, kissa", "2, koira", "3, lintu", "4, karhu"})
    public void testCardBackAtIndexIsCorrect(int index, String back) throws CardFlippedException {
        Card card = vocabulary.getCardByID(index);

        Assertions.assertTrue(card.check(back));
    }

    @Test
    public void testNoCardsRetrievedIfLanguageIsUnavailable() {
        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages("", "");

        Assertions.assertEquals(0, cardIDs.size());
    }

    @ParameterizedTest
    @CsvSource({"EN, FI, 4", "ES, FR, 3"})
    public void testCardsRetrievedIfLanguageMatches(
            String sourceLanguage,
            String targetLanguage,
            int size
    ) {
        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages(
                sourceLanguage, targetLanguage
                );

        Assertions.assertEquals(size, cardIDs.size());
    }

    @Test
    public void testCardsRetrievedForFlippedTranslationDirection(
            @Value("${deck.source-language}") String targetLanguage,
            @Value("${deck.target-language}") String sourceLanguage
    ) {
        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages(
                sourceLanguage, targetLanguage
        );

        Assertions.assertEquals(4, cardIDs.size());
    }

    @Test
    public void testFlippedCardsUseSecondSetOfIndices(
            @Value("${deck.source-language}") String targetLanguage,
            @Value("${deck.target-language}") String sourceLanguage
    ) {
        List<Integer> cardIDs = vocabulary.getAllCardIDsForLanguages(
                sourceLanguage, targetLanguage
        );

        Assertions.assertEquals(List.of(8, 9, 10, 11), cardIDs);
    }

    @ParameterizedTest
    @CsvSource({"1, kissa", "2, koira", "3, lintu", "4, karhu"})
    public void testFlippedCardFrontAtIndexIsCorrect(int index, String front) {
        Card card = vocabulary.getCardByID(index+7);

        Assertions.assertEquals(front, card.read());
    }

    @ParameterizedTest
    @CsvSource({"1, cat", "2, dog", "3, bird", "4, bear"})
    public void testFlippedCardBackAtIndexIsCorrect(int index, String back)
            throws CardFlippedException {
        Card card = vocabulary.getCardByID(index+7);

        Assertions.assertTrue(card.check(back));
    }

    @Test
    public void testAllLanguagesAreFound() {
        List<String> languages = vocabulary.getAllLanguages();

        Assertions.assertEquals(List.of("EN", "ES", "FI", "FR"), languages);
    }

    @ParameterizedTest
    @CsvSource({"EN, FI", "FI, EN", "ES, FR", "FR, ES"})
    public void testCorrectLanguageIsFoundForSource(String sourceLanguage, String targetLanguage) {
        List<String> targetLanguages = vocabulary.getTargetLanguagesForSource(sourceLanguage);

        Assertions.assertEquals(List.of(targetLanguage), targetLanguages);
    }
}
