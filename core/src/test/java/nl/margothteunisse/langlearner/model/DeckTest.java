package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.model.vocabularies.EmptyVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckTest {
    @Mock
    private IVocabulary vocabulary;

    @Test
    public void testCannotDrawFromEmptyDeck() {
        Deck deck = new Deck(new EmptyVocabulary());

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @Test
    public void testCanDrawFromNonEmptyDeck() {
        when(vocabulary.getAllCardIDs()).thenReturn(List.of(0));
        Deck deck = new Deck(vocabulary);

        boolean canDraw = deck.draw();

        Assertions.assertTrue(canDraw);
    }

    @Test
    public void testCardIsRemovedAfterDrawing() {
        when(vocabulary.getAllCardIDs()).thenReturn(List.of(0));
        Deck deck = new Deck(vocabulary);
        deck.draw();

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @ParameterizedTest
    @CsvSource({"cat, kissa", "dog, koira"})
    public void testDrawnCardMatchesVocabulary(String front, String back){
        Card inputCard = new Card(front, back);
        when(vocabulary.getAllCardIDs()).thenReturn(List.of(0));
        when(vocabulary.getCardByID(0)).thenReturn(inputCard);
        Deck deck = new Deck(vocabulary);

        deck.draw();
        Card card = deck.getDrawnCard();

        Assertions.assertEquals(inputCard, card);
    }

    @ParameterizedTest
    @ValueSource(strings = {"EN", "FI", "FR", "ES", ""})
    public void testCanRetrieveSourceLanguage(String input) {
        Deck deck = new Deck(new EmptyVocabulary(), input, "");

        String sourceLanguage = deck.getSourceLanguage();

        Assertions.assertEquals(input, sourceLanguage);
    }

    @ParameterizedTest
    @ValueSource(strings = {"EN", "FI", "FR", "ES", ""})
    public void testCanRetrieveTargetLanguage(String input) {
        Deck deck = new Deck(new EmptyVocabulary(), "", input);

        String targetLanguage = deck.getTargetLanguage();

        Assertions.assertEquals(input, targetLanguage);
    }

    @Test
    public void testDeckIsEmptyIfLanguagesNotAvailable() {
        Deck deck = new Deck(new EnglishToFinnishVocabulary(), "", "");

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @Test
    public void testDeckIsNonEmptyIfLanguagesAreAvailable() {
        Deck deck = new Deck(new EnglishToFinnishVocabulary(), "EN", "FI");

        boolean canDraw = deck.draw();

        Assertions.assertTrue(canDraw);
    }

    private class EnglishToFinnishVocabulary extends EmptyVocabulary {
        @Override
        public List<Integer> getAllCardIDsForLanguages(String sourceLanguage, String targetLanguage) {
            if (sourceLanguage.equals("EN") && targetLanguage.equals("FI")) {
                return List.of(0);
            }

            return List.of();
        }
    }
}