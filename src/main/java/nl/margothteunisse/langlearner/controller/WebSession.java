package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.exceptions.DeckEmptyException;
import nl.margothteunisse.langlearner.view.IView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/session")
public class WebSession extends Session{

    public WebSession(Deck deck, IView view) throws DeckEmptyException {
        super(deck, view);
        deck.draw();
    }

    @PostMapping("/draw-next-card")
    public ResponseEntity<Boolean> drawNextCard() {
        try {
            deck.draw();
            return ResponseEntity.ok().body(true);
        } catch (DeckEmptyException e) {
            return ResponseEntity.ok().body(false);
        }
    }

    @GetMapping("/fetch-word")
    public ResponseEntity<String> fetchWord() {
        String cardFront = deck.getDrawnCard().read();
        return ResponseEntity.ok().body(cardFront);
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<Boolean> submitAnswer(@RequestBody Map<String, String> requestBody) {
        boolean answerIsCorrect = deck.getDrawnCard().check(requestBody.get("answer"));
        return ResponseEntity.ok().body(answerIsCorrect);
    }
}
