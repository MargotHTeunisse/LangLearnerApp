package nl.margothteunisse.langlearner.dto;

import nl.margothteunisse.langlearner.model.Card;

public record CardDTO(int id, String visibleWord, Boolean answerIsCorrect, boolean answerIsVisible) {
    public static CardDTO of(int id, Card card) {
        if (card == null) {
            return null;
        }
        else {
            return new CardDTO(id, card.read(), card.getFlipped());
        }
    }

    public CardDTO(int id, String visibleWord) {
        this(id, visibleWord, null, false);
    }

    public CardDTO(int id, String visibleWord, boolean answerIsVisible) {
        this(id, visibleWord, null, answerIsVisible);
    }
}
