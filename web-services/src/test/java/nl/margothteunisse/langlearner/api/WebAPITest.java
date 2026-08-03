package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.view.CardView;
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
    }

    @MockitoBean("userDeck")
    private Deck userDeck;

    @Autowired
    private WebAPI api;

    @Test
    public void testDrawnCardIsRead() {
        String front = "cat";
        when(userDeck.getDrawnCard())
                .thenReturn(new Card(front, ""));

        CardView cardView = api.drawnCard();

        Assertions.assertEquals(
                front,
                cardView.visibleWord);
    }

    @Test
    public void testAnswerIsCorrectIfMatchesBack() {
        String back = "kissa";
        when(userDeck.getDrawnCard())
                .thenReturn(new Card("", "kissa"));

        boolean answerIsCorrect = api.submitAnswer(back)
                .getBody()
                .get("answerIsCorrect");

        Assertions.assertTrue(answerIsCorrect);
    }

    @Test
    public void testDeckIsDepletedIfDrawReturnsFalse() {
        when(userDeck.draw())
                .thenReturn(false);
        MockHttpSession session = new MockHttpSession();

        boolean deckIsDepleted = api.drawNextCard(session)
                .get("deckIsDepleted");

        Assertions.assertTrue(deckIsDepleted);
    }

    @Test
    public void testSessionIsInvalidatedIfDeckIsDepleted() {
        when(userDeck.draw())
                .thenReturn(false);
        MockHttpSession session = new MockHttpSession();


        api.drawNextCard(session);

        Assertions.assertTrue(session.isInvalid());
    }
}
