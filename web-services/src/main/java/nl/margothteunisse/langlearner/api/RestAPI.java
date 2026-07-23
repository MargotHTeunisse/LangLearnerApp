package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
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

    @PatchMapping("/draw-next-card")
    public Boolean drawNextCard() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");
        return deck.draw();
    }

    @PostMapping("/close")
    public void closeSession(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/fetch-word")
    public String fetchWord() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");
        String cardFront = deck.getDrawnCard().read();
        return cardFront;
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
