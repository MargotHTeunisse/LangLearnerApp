package nl.margothteunisse.langlearner.model;

import java.util.Collection;
import java.util.List;

public class EmptyVocabulary extends Vocabulary{
    @Override
    Card getCardByID(int cardID) {
        return null;
    }

    @Override
    Collection<Integer> getAllCardIDs() {
        return List.of();
    }
}
