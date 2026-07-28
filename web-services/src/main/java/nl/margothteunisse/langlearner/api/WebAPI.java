package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.view.CardView;
import nl.margothteunisse.langlearner.view.DeckView;
import nl.margothteunisse.langlearner.view.FeedbackView;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class WebAPI implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @GetMapping("/fetch-deck")
    @ResponseBody
    public DeckView fetchDeck() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        Card card = deck.getDrawnCard();
        return new DeckView(card.read(), card.getFlipped(), false);
    }

    @PostMapping("/draw-next-card")
    @ResponseBody
    public DeckView drawNextCard(HttpSession session) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");

        boolean deckIsDepleted = !deck.draw();

        if (deckIsDepleted) {
            session.invalidate();
        }

        Card card = deck.getDrawnCard();
        return new DeckView(card.read(), card.getFlipped(), deckIsDepleted);
    }

    @GetMapping("/submit")
    @ResponseBody
    public ResponseEntity<FeedbackView> submitAnswer(@RequestParam String answer) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");
        Card card = deck.getDrawnCard();
        String cardFront = card.read();
        try {
            boolean answerIsCorrect = card.check(answer);
            FeedbackView feedback = new FeedbackView(cardFront, false, answerIsCorrect);
            return ResponseEntity.ok().body(feedback);
        } catch (CardFlippedException e) {
            FeedbackView feedback = new FeedbackView(cardFront, true, false);
            return ResponseEntity.badRequest().body(feedback);
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
