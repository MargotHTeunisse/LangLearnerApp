package nl.margothteunisse.langlearner.model;

import java.util.List;

public interface IVocabulary {
    Card getCardByID(int cardID);
    List<Integer> getAllCardIDs();
}
