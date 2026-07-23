package model;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.IVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}

class EmptyVocabulary implements IVocabulary {
    @Override
    public Card getCardByID(int cardID) {
        return null;
    }

    @Override
    public List<Integer> getAllCardIDs() {
        return List.of();
    }
}

class SingleCardNullVocabulary extends EmptyVocabulary {
    @Override
    public List<Integer> getAllCardIDs() {
        return List.of(0);
    }
}

class SingleCardVocabulary extends SingleCardNullVocabulary {
    private final Card card;

    SingleCardVocabulary(Card card) {
        this.card = card;
    }

    @Override
    public Card getCardByID(int cardID) {
        return card;
    }
}
