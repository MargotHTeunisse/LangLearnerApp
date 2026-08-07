package nl.margothteunisse.langlearner.dto;

public record DeckDTO(CardDTO card, boolean deckIsDepleted) {
    public DeckDTO(String visibleWord, boolean deckIsDepleted) {
        this(new CardDTO(visibleWord), deckIsDepleted);
    }
}
