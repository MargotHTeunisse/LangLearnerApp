package nl.margothteunisse.langlearner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("web")
public class LangLearnerApp {

    public static void main(String[] args){
        SpringApplication.run(LangLearnerApp.class);
    }
}
