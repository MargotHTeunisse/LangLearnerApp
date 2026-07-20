package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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

    @GetMapping("/fetch-word")
    public String fetchWord() {
        Deck deck = (Deck) applicationContext.getBean("userDeck");
        String cardFront = deck.getDrawnCard().read();
        return cardFront;
    }

    @PostMapping("/submit")
    public Boolean submitAnswer(@RequestParam String answer) {
        Deck deck = (Deck) applicationContext.getBean("userDeck");
        boolean answerIsCorrect = deck.getDrawnCard().check(answer);
        return answerIsCorrect;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
