package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.view.DeckView;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class RestAPI implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @GetMapping("/fetch-deck")
    public DeckView fetchDeck() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new DeckView(card.read(), false, card.getFlipped());
    }

    @PostMapping("/draw-next-card")
    public DeckView drawNextCard(HttpSession session) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        boolean deckIsDepleted = !deck.draw();

        if (deckIsDepleted) {
            session.invalidate();
        }

        Card card = deck.getDrawnCard();
        return new DeckView(card.read(), deckIsDepleted, card.getFlipped());
    }

    @PostMapping("/show-answer")
    public DeckView showAnswer() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        card.flip();

        return new DeckView(card.read(), false, card.getFlipped());
    }

    @PostMapping("/submit")
    public ResponseEntity<Boolean> submitAnswer(@RequestParam String answer) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");
        boolean answerIsCorrect;
        try {
            answerIsCorrect = deck.getDrawnCard().check(answer);
            return ResponseEntity.ok().body(answerIsCorrect);
        } catch (CardFlippedException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
