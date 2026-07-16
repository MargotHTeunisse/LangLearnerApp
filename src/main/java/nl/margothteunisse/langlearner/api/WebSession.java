package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("web")
@RestController
@RequestMapping("/session")
public class WebSession {
    private final Deck deck;

    public WebSession(Deck deck) {
        this.deck = deck;
        deck.draw();
    }

    @PatchMapping("/draw-next-card")
    public ResponseEntity<Boolean> drawNextCard() {
        return ResponseEntity.ok().body(deck.draw());
    }

    @GetMapping("/fetch-word")
    public ResponseEntity<String> fetchWord() {
        String cardFront = deck.getDrawnCard().read();
        return ResponseEntity.ok().body(cardFront);
    }

    @PostMapping("/submit")
    public ResponseEntity<Boolean> submitAnswer(@RequestParam String answer) {
        boolean answerIsCorrect = deck.getDrawnCard().check(answer);
        return ResponseEntity.ok().body(answerIsCorrect);
    }
}
