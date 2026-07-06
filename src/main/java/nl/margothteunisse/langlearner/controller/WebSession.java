package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/session")
public class WebSession extends Session implements AutoCloseable{

    public WebSession(Deck deck) {
        super(deck);
    }

    @PostMapping("/draw-next-card")
    public ResponseEntity<Boolean> drawNextCard() {
        return ResponseEntity.ok().body(deck.draw());
    }

    @GetMapping("/fetch-word")
    public ResponseEntity<String> fetchWord() {
        String cardFront = deck.readCard();
        return ResponseEntity.ok().body(cardFront);
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<Boolean> submitAnswer(@RequestBody Map<String, String> requestBody) {
        boolean answerIsCorrect = deck.translateCard(requestBody.get("answer"));
        return ResponseEntity.ok().body(answerIsCorrect);
    }
}
