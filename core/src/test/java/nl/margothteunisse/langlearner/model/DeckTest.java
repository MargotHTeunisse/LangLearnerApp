package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.model.vocabularies.stubs.EmptyVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class DeckTest {

    @Test
    public void testCannotDrawFromEmptyDeck() {
        Deck deck = new Deck(new EmptyVocabulary());

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @Test
    public void testCanDrawFromNonEmptyDeck() {
        Deck deck = new Deck(new SingleCardNullVocabulary());

        boolean canDraw = deck.draw();

        Assertions.assertTrue(canDraw);
    }

    @Test
    public void testCardIsRemovedAfterDrawing() {
        Deck deck = new Deck(new SingleCardNullVocabulary());
        deck.draw();

        boolean canDraw = deck.draw();

        Assertions.assertFalse(canDraw);
    }

    @ParameterizedTest
    @CsvSource({"cat, kissa", "dog, koira"})
    public void testDrawnCardMatchesVocabulary(String front, String back){
        Card inputCard = new Card(front, back);
        Deck deck = new Deck(new SingleCardVocabulary(inputCard));

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

    private class SingleCardNullVocabulary extends EmptyVocabulary {
        @Override
        public List<Integer> getAllCardIDs() {
            return List.of(0);
        }
    }

    private class SingleCardVocabulary extends SingleCardNullVocabulary {
        private final Card card;

        SingleCardVocabulary(Card card) {
            this.card = card;
        }

        @Override
        public Card getCardByID(int cardID) {
            return card;
        }
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