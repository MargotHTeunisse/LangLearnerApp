package nl.margothteunisse.langlearner.dto;

public record CardDTO(String visibleWord, Boolean answerIsCorrect) {
    public CardDTO(String visibleWord) {
        this(visibleWord, null);
    }
}
