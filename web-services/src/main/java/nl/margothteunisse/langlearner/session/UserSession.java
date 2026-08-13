package nl.margothteunisse.langlearner.session;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.IVocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private Deck deck;

    @Autowired
    private IVocabulary vocabulary;

    public UserSession(Deck deck) {
        this.deck = deck;
        this.deck.draw();
    }

    public Deck getDeck() {
        return this.deck;
    }

    public void refreshDeck() {
        deck = new Deck(vocabulary, deck.getSourceLanguage(), deck.getTargetLanguage());
        deck.draw();
    }

    public void flipTranslationDirection() {
        deck = new Deck(vocabulary, deck.getTargetLanguage(), deck.getSourceLanguage());
        deck.draw();
    }

    public void changeLanguage(String sourceLanguage, String targetLanguage) {
        deck = new Deck(vocabulary, sourceLanguage, targetLanguage);
        deck.draw();
    }
}
