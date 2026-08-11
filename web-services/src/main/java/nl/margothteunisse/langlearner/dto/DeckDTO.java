package nl.margothteunisse.langlearner.dto;

public record DeckDTO(CardDTO card, String sourceLanguage, String targetLanguage, boolean deckIsDepleted) {
    public DeckDTO(String visibleWord, String sourceLanguage, String targetLanguage, boolean deckIsDepleted) {
        this(new CardDTO(visibleWord), sourceLanguage, targetLanguage, deckIsDepleted);
    }
}
