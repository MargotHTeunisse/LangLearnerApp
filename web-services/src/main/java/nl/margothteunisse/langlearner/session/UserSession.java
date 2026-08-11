package nl.margothteunisse.langlearner.session;

import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private Deck deck;

    public UserSession(Deck deck) {
        this.deck = deck;
        this.deck.draw();
    }

    public Deck getDeck() {
        return this.deck;
    }
}
