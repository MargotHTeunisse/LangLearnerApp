package nl.margothteunisse.langlearner.dto;

import nl.margothteunisse.langlearner.model.Card;

public record CardDTO(String visibleWord, Boolean answerIsCorrect, boolean answerIsVisible) {
    public static CardDTO of(Card card) {
        if (card == null) {
            return null;
        }
        else {
            return new CardDTO(card.read(), card.getFlipped());
        }
    }

    public CardDTO(String visibleWord) {
        this(visibleWord, null, false);
    }

    public CardDTO(String visibleWord, boolean answerIsVisible) {
        this(visibleWord, null, answerIsVisible);
    }
}
