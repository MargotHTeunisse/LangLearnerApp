package nl.margothteunisse.langlearner.view;

import org.springframework.http.ResponseEntity;

public interface IView {
    ResponseEntity<String> display();

    void close();

    void updateCard(String cardFront);

    void updateCorrect();

    void updateIncorrect(String cardBack);
}
