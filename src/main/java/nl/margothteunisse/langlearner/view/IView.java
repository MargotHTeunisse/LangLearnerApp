package nl.margothteunisse.langlearner.view;

public interface IView {
    String display();

    void close();

    void updateCard(String cardFront);

    void updateCorrect();

    void updateIncorrect(String cardBack);
}
