package nl.margothteunisse.langlearner.dto;

public record CardDTO(String visibleWord, Boolean answerIsCorrect, boolean answerIsVisible) {
    public CardDTO(String visibleWord) {
        this(visibleWord, null, false);
    }

    public CardDTO(String visibleWord, boolean answerIsVisible) {
        this(visibleWord, null, answerIsVisible);
    }
}
