package nl.margothteunisse.langlearner.model;

import nl.margothteunisse.langlearner.model.vocabularies.EmptyVocabulary;
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
