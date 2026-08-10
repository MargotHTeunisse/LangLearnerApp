package nl.margothteunisse.langlearner.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Deck {
    private final IVocabulary vocabulary;
    private final List<Integer> cardsInDeck = new ArrayList<>();
    private Card drawnCard;
    private String sourceLanguage;
    private String targetLanguage;

    public Deck(IVocabulary vocabulary) {
        this.vocabulary = vocabulary;

        cardsInDeck.addAll(vocabulary.getAllCardIDs());
    }

    @Autowired
    public Deck(IVocabulary vocabulary, String sourceLanguage, String targetLanguage) {
        this.vocabulary = vocabulary;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;

        cardsInDeck.addAll(vocabulary.getAllCardIDsForLanguages(sourceLanguage, targetLanguage));
    }

    public Card getDrawnCard() {return drawnCard;}

    public boolean draw() {
        if (cardsInDeck.isEmpty()) {
            return false;
        }
        else {
            drawnCard = vocabulary.getCardByID(cardsInDeck.get(0));
            cardsInDeck.remove(0);
            return true;
        }
    }

    public String getSourceLanguage() {
        return this.sourceLanguage;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }
}
