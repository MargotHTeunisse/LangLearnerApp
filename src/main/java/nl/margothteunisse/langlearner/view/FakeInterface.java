package nl.margothteunisse.langlearner.view;

public class FakeInterface implements UserInterface {
    @Override
    public void showCorrect() {

    }

    @Override
    public void showIncorrect(String correctWord) {

    }

    @Override
    public String askForTranslation(String word) {
        return "";
    }
}
