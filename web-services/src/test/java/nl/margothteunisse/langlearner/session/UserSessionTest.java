package nl.margothteunisse.langlearner.session;

import nl.margothteunisse.langlearner.model.Deck;
import nl.margothteunisse.langlearner.model.vocabularies.EmptyVocabulary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes={UserSession.class, EmptyVocabulary.class})
public class UserSessionTest {
    @MockitoBean
    private Deck deck;

    @ParameterizedTest
    @CsvSource({"EN, FI", "ES, FR", ","})
    public void testLanguagesAreCorrectAfterChangingLanguage(String sourceLanguage,
                                                             String targetLanguage,
                                                             @Autowired UserSession userSession) {
        userSession.changeLanguage(sourceLanguage, targetLanguage);

        Deck deck = userSession.getDeck();
        Assertions.assertEquals(sourceLanguage, deck.getSourceLanguage());
        Assertions.assertEquals(targetLanguage, deck.getTargetLanguage());
    }
}
