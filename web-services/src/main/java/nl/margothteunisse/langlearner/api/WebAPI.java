package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.view.CardView;
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
    public CardView drawnCard() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new CardView(card.read(), card.getFlipped());
    }

    @GetMapping("/submit")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> submitAnswer(@RequestParam String answer) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        try {
            boolean answerIsCorrect = deck.getDrawnCard().check(answer);
            return ResponseEntity.ok().body(Map.of("answerIsCorrect", answerIsCorrect));
        } catch (CardFlippedException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot submit answer if answer is visible."
            );
        }
    }

    @PostMapping("/draw-next-card")
    @ResponseBody
    public Map<String, Boolean> drawNextCard(HttpSession session) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        if (deck.draw()) {
            return Map.of("deckIsDepleted", false);
        }
        else {
            session.invalidate();
            return Map.of("deckIsDepleted", true);
        }


    }

    @PostMapping("/show-answer")
    @ResponseBody
    public CardView showAnswer() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        card.flip();

        return new CardView(card.read(), card.getFlipped());
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
