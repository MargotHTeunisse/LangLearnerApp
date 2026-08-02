package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseBody
    @ExceptionHandler(CardFlippedException.class)
    public ResponseEntity<Map<String, String>> exceptionCardFlippedHandler() {
        return ResponseEntity.badRequest().body(
                Map.of("message", "Cannot submit answer if answer is visible.")
        );
    }
}
