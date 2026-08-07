package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.dto.DeckDTO;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.dto.CardDTO;
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

    @GetMapping("/drawn-card")
    @ResponseBody
    public CardDTO drawnCard() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new CardDTO(card.read(), card.getFlipped());
    }

    @GetMapping("/submit")
    @ResponseBody
    public CardDTO submitAnswer(@RequestParam String answer) throws CardFlippedException {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new CardDTO(card.read(), card.check(answer), card.getFlipped());
    }

    @PostMapping("/draw-next-card")
    @ResponseBody
    public DeckDTO drawNextCard(HttpSession session) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        boolean deckIsDepleted = !deck.draw();
        if (deckIsDepleted) {
            session.invalidate();
        }

        return new DeckDTO(deck.getDrawnCard().read(), deckIsDepleted);
    }

    @PostMapping("/show-answer")
    @ResponseBody
    public CardDTO showAnswer() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        card.flip();

        return new CardDTO(card.read(), card.getFlipped());
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
