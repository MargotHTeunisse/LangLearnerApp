package nl.margothteunisse.langlearner.config;

import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class WebConfig {
    @Bean
    @SessionScope
    public Deck userDeck(Deck deck) {
        deck.draw();
        return deck;
    }
}
