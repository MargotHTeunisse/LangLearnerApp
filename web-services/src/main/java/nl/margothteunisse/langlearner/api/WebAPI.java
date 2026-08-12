package nl.margothteunisse.langlearner.api;

import jakarta.servlet.http.HttpSession;
import nl.margothteunisse.langlearner.dto.DeckDTO;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.dto.CardDTO;
import nl.margothteunisse.langlearner.session.UserSession;
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

    @GetMapping("/drawn-card")
    @ResponseBody
    public DeckDTO drawnCard() {
        Deck deck = applicationContext.getBean(UserSession.class).getDeck();

        Card card = deck.getDrawnCard();
        return new DeckDTO(new CardDTO(card.read(), card.getFlipped()),
                deck.getSourceLanguage(), deck.getTargetLanguage(), false);
    }

    @GetMapping("/submit")
    @ResponseBody
    public CardDTO submitAnswer(@RequestParam String answer) throws CardFlippedException {
        Deck deck = applicationContext.getBean(UserSession.class).getDeck();

        Card card = deck.getDrawnCard();
        return new CardDTO(card.read(), card.check(answer), card.getFlipped());
    }

    @PostMapping("/draw-next-card")
    @ResponseBody
    public DeckDTO drawNextCard(HttpSession session) {
        UserSession userSession = applicationContext.getBean(UserSession.class);
        Deck deck = userSession.getDeck();

        boolean deckIsDepleted = !deck.draw();
        if (deckIsDepleted) {
            session.invalidate();
        }

        deck = userSession.getDeck();

        return new DeckDTO(deck.getDrawnCard().read(), deck.getSourceLanguage(), deck.getTargetLanguage(),
                deckIsDepleted);
    }

    @PostMapping("/show-answer")
    @ResponseBody
    public CardDTO showAnswer() {
        Deck deck = applicationContext.getBean(UserSession.class).getDeck();

        Card card = deck.getDrawnCard();
        card.flip();

        return new CardDTO(card.read(), card.getFlipped());
    }

    @PostMapping("/flip-translation-direction")
    @ResponseBody
    public DeckDTO flipTranslationDirection() {
        UserSession userSession = applicationContext.getBean(UserSession.class);

        userSession.flipTranslationDirection();

        Deck deck = userSession.getDeck();
        return new DeckDTO(deck.getDrawnCard().read(),
                deck.getSourceLanguage(), deck.getTargetLanguage(), false);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
