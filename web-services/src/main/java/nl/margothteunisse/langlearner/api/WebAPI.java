package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.dto.DeckDTO;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.dto.CardDTO;
import nl.margothteunisse.langlearner.session.UserSession;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class WebAPI implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @GetMapping("/drawn-card")
    @ResponseBody
    public DeckDTO drawnCard() {
        Deck deck = applicationContext.getBean(UserSession.class).getDeck();

        return new DeckDTO(CardDTO.of(deck.getDrawnCard()),
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
    public DeckDTO drawNextCard() {
        UserSession userSession = applicationContext.getBean(UserSession.class);
        Deck deck = userSession.getDeck();

        boolean deckIsDepleted = !deck.draw();
        if (deckIsDepleted) {
            userSession.refreshDeck();
        }

        deck = userSession.getDeck();

        return new DeckDTO(CardDTO.of(deck.getDrawnCard()), deck.getSourceLanguage(), deck.getTargetLanguage(),
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
        return new DeckDTO(CardDTO.of(deck.getDrawnCard()),
                deck.getSourceLanguage(), deck.getTargetLanguage(), false);
    }

    @PostMapping("/change-language")
    @ResponseBody
    public DeckDTO changeLanguage(@RequestParam String sourceLanguage,
                                  @RequestParam String targetLanguage) {
        UserSession userSession = applicationContext.getBean(UserSession.class);

        userSession.changeLanguage(sourceLanguage, targetLanguage);

        Deck deck = userSession.getDeck();
        return new DeckDTO(CardDTO.of(deck.getDrawnCard()),
                deck.getSourceLanguage(), deck.getTargetLanguage(), false);
    }

    @GetMapping("/language-options")
    @ResponseBody
    public Map<String, List<String>> languageOptions() {
        IVocabulary vocabulary = applicationContext.getBean(IVocabulary.class);

        Map<String, List<String>> languageOptions = new HashMap<>();
        for (String sourceLanguage: vocabulary.getAllLanguages()) {
            languageOptions.put(sourceLanguage,
                    vocabulary.getTargetLanguagesForSource(sourceLanguage));
        }

        return languageOptions;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
