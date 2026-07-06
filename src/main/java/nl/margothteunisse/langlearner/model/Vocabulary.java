package nl.margothteunisse.langlearner.model;

import java.util.Collection;

abstract class Vocabulary {
    abstract Card getCardByID(int cardID);
    abstract Collection<Integer> getAllCardIDs();
}
