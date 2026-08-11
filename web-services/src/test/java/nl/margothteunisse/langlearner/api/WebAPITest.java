package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.dto.CardDTO;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import nl.margothteunisse.langlearner.session.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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
        public UserSession userSession() {
            return new UserSession();
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

        CardDTO cardView = api.drawnCard();

        Assertions.assertEquals(
                front,
                cardView.visibleWord());
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
        MockHttpSession session = new MockHttpSession();

        boolean deckIsDepleted = api.drawNextCard(session).deckIsDepleted();

        Assertions.assertTrue(deckIsDepleted);
    }

    @Test
    public void testSessionIsInvalidatedIfDeckIsDepleted() {
        when(deck.draw())
                .thenReturn(false);
        when(deck.getDrawnCard())
                .thenReturn(new Card("", ""));
        MockHttpSession session = new MockHttpSession();


        api.drawNextCard(session);

        Assertions.assertTrue(session.isInvalid());
    }
}
