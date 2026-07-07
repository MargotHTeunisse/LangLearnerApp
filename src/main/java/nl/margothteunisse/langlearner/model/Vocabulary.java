package nl.margothteunisse.langlearner.model;

import java.util.List;

interface Vocabulary {
    Card getCardByID(int cardID);
    List<Integer> getAllCardIDs();
}
