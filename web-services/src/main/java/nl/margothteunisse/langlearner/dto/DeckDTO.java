package nl.margothteunisse.langlearner.dto;

public record DeckDTO(CardDTO card, boolean deckIsDepleted) {
    public DeckDTO(String card, boolean deckIsDepleted) {
        this(new CardDTO(card), deckIsDepleted);
    }
}
