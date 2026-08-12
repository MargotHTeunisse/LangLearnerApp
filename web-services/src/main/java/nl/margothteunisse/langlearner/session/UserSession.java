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

    public void flipTranslationDirection() {
        deck = new Deck(vocabulary, deck.getTargetLanguage(), deck.getSourceLanguage());
        deck.draw();
    }
}
