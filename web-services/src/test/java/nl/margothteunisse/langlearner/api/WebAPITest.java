package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.dto.DeckDTO;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.model.vocabularies.EmptyVocabulary;
import nl.margothteunisse.langlearner.session.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(WebAPITest.Config.class)
public class WebAPITest {
    @Configuration
    static class Config {
        @Bean
        public WebAPI api() {
            return new WebAPI();
        }

        @Bean
        @Scope("prototype")
        public UserSession userSession(Deck deck) {
            return new UserSession(deck);
        }

        @Bean
        public IVocabulary vocabulary() {
            return new EmptyVocabulary();
        }
    }

    @MockitoBean
    private Deck deck;

    @Autowired
    private WebAPI api;

    @Test
    public void testDrawnCardIsRead() {
        String front = "cat";
        when(deck.getDrawnCard())
                .thenReturn(new Card(front, ""));

        DeckDTO deckDTO = api.drawnCard();

        Assertions.assertEquals(
                front,
                deckDTO.card().visibleWord());
    }

    @Test
    public void testCardIsNullIfNoCardExists() {
        when(deck.getDrawnCard())
                .thenReturn(null);

        DeckDTO deckDTO = api.drawnCard();

        Assertions.assertNull(deckDTO.card());
    }

    @Test
    public void testAnswerIsCorrectIfMatchesBack() throws CardFlippedException {
        String back = "kissa";
        when(deck.getDrawnCard())
                .thenReturn(new Card("", "kissa"));

        boolean answerIsCorrect = api.submitAnswer(back).answerIsCorrect();

        Assertions.assertTrue(answerIsCorrect);
    }

    @Test
    public void testDeckIsDepletedIfDrawReturnsFalse() {
        when(deck.draw())
                .thenReturn(false);
        when(deck.getDrawnCard())
                .thenReturn(new Card("", ""));

        boolean deckIsDepleted = api.drawNextCard().deckIsDepleted();

        Assertions.assertTrue(deckIsDepleted);
    }

    @ParameterizedTest
    @ValueSource(strings={"EN", "FR", "ES", "FI"})
    public void testSourceLanguageIsTargetAfterFlippingDirection(String sourceLanguage) {
        when(deck.getSourceLanguage()).thenReturn(sourceLanguage);
        when(deck.getTargetLanguage()).thenReturn("");

        DeckDTO deckDTO = api.flipTranslationDirection();

        Assertions.assertEquals(sourceLanguage, deckDTO.targetLanguage());
    }

    @ParameterizedTest
    @ValueSource(strings={"EN", "FR", "ES", "FI"})
    public void testTargetLanguageIsSourceAfterFlippingDirection(String targetLanguage) {
        when(deck.getSourceLanguage()).thenReturn("");
        when(deck.getTargetLanguage()).thenReturn(targetLanguage);

        DeckDTO deckDTO = api.flipTranslationDirection();

        Assertions.assertEquals(targetLanguage, deckDTO.sourceLanguage());
    }

    @ParameterizedTest
    @CsvSource({"EN, FI", "ES, FR", ","})
    public void testLanguagesAreCorrectAfterChangingLanguage(String sourceLanguage,
                                                             String targetLanguage) {
        DeckDTO deckDTO = api.changeLanguage(sourceLanguage, targetLanguage);

        Assertions.assertEquals(sourceLanguage, deckDTO.sourceLanguage());
        Assertions.assertEquals(targetLanguage, deckDTO.targetLanguage());
    }

    @ParameterizedTest
    @CsvSource({"EN, FI", "ES, FR", ","})
    public void testLanguageInformationIsRetainedAfterDeckIsRefreshed(String sourceLanguage,
                                                                     String targetLanguage) {
        when(deck.getSourceLanguage()).thenReturn(sourceLanguage);
        when(deck.getTargetLanguage()).thenReturn(targetLanguage);

        DeckDTO deckDTO = api.drawNextCard();

        reset(deck);

        Assertions.assertEquals(sourceLanguage, deckDTO.sourceLanguage());
        Assertions.assertEquals(targetLanguage, deckDTO.targetLanguage());
    }
}
