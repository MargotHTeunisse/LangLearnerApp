package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.dto.CardDTO;
import nl.margothteunisse.langlearner.dto.DeckDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public CardDTO drawnCard() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new CardDTO(card.read());
    }

    @GetMapping("/submit")
    @ResponseBody
    public CardDTO submitAnswer(@RequestParam String answer) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        boolean answerIsCorrect = card.check(answer);
        return new CardDTO(card.read(), answerIsCorrect);

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
}
