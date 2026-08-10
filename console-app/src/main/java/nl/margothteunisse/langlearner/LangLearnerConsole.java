package nl.margothteunisse.langlearner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LangLearnerConsole {

    @Value("${deck.source-language}")
    private String sourceLanguage;

    @Value("${deck.target-language}")
    private String targetLanguage;

    public static void main(String[] args) {

        SpringApplication.run(LangLearnerConsole.class).close();
    }

    @Bean
    public String sourceLanguage() {
        return this.sourceLanguage;
    }

    @Bean
    public String targetLanguage() {
        return this.targetLanguage;
    }
}
