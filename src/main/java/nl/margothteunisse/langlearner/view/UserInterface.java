package nl.margothteunisse.langlearner.view;

public interface UserInterface {
    void showCorrect();
    void showIncorrect(String correctWord);
    String askForTranslation(String word);
}
