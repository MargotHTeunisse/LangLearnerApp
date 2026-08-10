package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.vocabularies.InternalTextVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes={Deck.class, InternalTextVocabulary.class})
@ActiveProfiles(profiles="text")
@TestPropertySource(locations="classpath:test.properties")
public class IntegrationTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void testDeckIsPrototypeScoped() {
        Deck firstDeck = context.getBean(Deck.class);

        Deck secondDeck = context.getBean(Deck.class);

        Assertions.assertNotEquals(firstDeck, secondDeck);
    }
}
