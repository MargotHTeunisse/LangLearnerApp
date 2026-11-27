package nl.margothteunisse.langlearner.model;

import java.util.List;

public interface IVocabulary {
    Card getCardByID(int cardID);
    List<Integer> getAllCardIDs();
    List<Integer> getAllCardIDsForLanguages(String sourceLanguage, String targetLanguage);
    List<String> getAllLanguages();

    List<String> getTargetLanguagesForSource(String sourceLanguage);
}
