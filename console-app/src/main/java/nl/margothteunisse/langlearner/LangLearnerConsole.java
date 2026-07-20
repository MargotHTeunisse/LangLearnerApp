package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.model.Deck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class LangLearnerConsole {

    public static void main(String[] args) {
        SpringApplication.run(LangLearnerConsole.class).close();
    }
}
