package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;

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
