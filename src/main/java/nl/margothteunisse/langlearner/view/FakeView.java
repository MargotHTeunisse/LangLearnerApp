package nl.margothteunisse.langlearner.view;

public class FakeView implements IView {

    @Override
    public String display() {
        return "";
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
