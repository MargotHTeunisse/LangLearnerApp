package nl.margothteunisse.langlearner;

import nl.margothteunisse.langlearner.config.TextVocabularyConfig;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.vocabularies.TextVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes={Deck.class, TextVocabulary.class, TextVocabularyConfig.class})
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

    @Test
    public void testDefaultDeckIsNotEmpty() {
        Deck deck = context.getBean(Deck.class);

        boolean canDraw = deck.draw();

        Assertions.assertTrue(canDraw);
    }

    @Test
    public void testDefaultDeckHasSourceLanguageLabel() {
        Deck deck = context.getBean(Deck.class);

        String sourceLanguage = deck.getSourceLanguage();

        Assertions.assertNotNull(sourceLanguage);
    }

    @Test
    public void testDefaultDeckHasTargetLanguageLabel() {
        Deck deck = context.getBean(Deck.class);

        String targetLanguage = deck.getTargetLanguage();

        Assertions.assertNotNull(targetLanguage);
    }
}
