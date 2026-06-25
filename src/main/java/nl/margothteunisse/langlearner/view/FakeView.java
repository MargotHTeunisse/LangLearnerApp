package nl.margothteunisse.langlearner.view;

import org.springframework.http.ResponseEntity;

public class FakeView implements IView {

    @Override
    public ResponseEntity<String> display() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void updateCard(String cardFront) {

    }

    @Override
    public void updateCorrect() {

    }

    @Override
    public void updateIncorrect(String cardBack) {

    }
}
