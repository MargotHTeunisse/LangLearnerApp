package nl.margothteunisse.langlearner.controller;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.view.IView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class WebSession extends Session{
    public WebSession(Deck deck, IView view) {
        super(deck, view);
    }

    @RequestMapping("/")
    public ResponseEntity<String> refresh() {
        return super.getUpdatedView("");
    }

    @RequestMapping("/{answer}")
    public ResponseEntity<String> giveAnswer(@PathVariable("answer") String answer) {
        return super.getUpdatedView(answer);
    }
}
