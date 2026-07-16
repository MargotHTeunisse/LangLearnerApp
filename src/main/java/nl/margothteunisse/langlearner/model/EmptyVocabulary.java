package nl.margothteunisse.langlearner.model;

import java.util.List;

public class EmptyVocabulary implements IVocabulary {
    @Override
    public Card getCardByID(int cardID) {
        return null;
    }

    @Override
    public List<Integer> getAllCardIDs() {
        return List.of();
    }
}
