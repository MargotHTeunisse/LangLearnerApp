package nl.margothteunisse.langlearner.config;

import nl.margothteunisse.langlearner.model.Deck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes={WebConfig.class})
public class WebConfigTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void testUserDeckExists() {
        boolean userDeckIsDeck = context.isTypeMatch("userDeck", Deck.class);

        Assertions.assertTrue(userDeckIsDeck);
    }

}
