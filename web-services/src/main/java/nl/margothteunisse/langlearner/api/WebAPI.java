package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.view.CardView;
import nl.margothteunisse.langlearner.view.DeckView;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class WebAPI implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/drawn-card")
    @ResponseBody
    public CardView drawnCard() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new CardView(card.read());
    }

    @GetMapping("/submit")
    @ResponseBody
    public CardView submitAnswer(@RequestParam String answer) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        boolean answerIsCorrect = card.check(answer);
        return new CardView(card.read(), answerIsCorrect);

    }

    @PostMapping("/draw-next-card")
    @ResponseBody
    public DeckView drawNextCard(HttpSession session) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        boolean deckIsDepleted = !deck.draw();
        if (deckIsDepleted) {
            session.invalidate();
        }

        return new DeckView(deck.getDrawnCard().read(), deckIsDepleted);
    }
}
