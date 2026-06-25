package nl.margothteunisse.langlearner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="nl.margothteunisse.langlearner")
public class LangLearnerApp {

    public static void main(String[] args){
        SpringApplication.run(LangLearnerApp.class);
    }

    @Bean ("vocabularyName")
    String getVocabularyName() {
        return "wordlist.txt";
    }
}
