package nl.margothteunisse.langlearner.api;

import nl.margothteunisse.langlearner.dto.CardDTO;
import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import nl.margothteunisse.langlearner.model.exceptions.CardFlippedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@RestController
public class RestAPI {
    @Autowired
    IVocabulary vocabulary;

    @GetMapping("card")
    public CardDTO getCardById(@RequestParam int id) {
        Card card = vocabulary.getCardByID(id);

        return CardDTO.of(card);
    }

    @GetMapping("feedback")
    public CardDTO submitAnswer(@RequestParam int id, @RequestParam String answer)
            throws CardFlippedException {
        Card card = vocabulary.getCardByID(id);

        return new CardDTO(card.read(), card.check(answer), false);
    }

    @GetMapping("answer")
    public CardDTO showAnswer(@RequestParam int id) {
        Card card = vocabulary.getCardByID(id);

        card.flip();

        return new CardDTO(card.read(), true);
    }

    @GetMapping("all-card-ids")
    public Map<String, List<Integer>> getAllCardIds() {
        return Map.ofEntries(entry("cardIDs", vocabulary.getAllCardIDs()));
    }

    @GetMapping("all-card-ids-for-languages")
    public Map<String, List<Integer>> getAllCardIdsForLanguages(@RequestParam String source,
                                                                @RequestParam String target) {
        return Map.ofEntries(entry("cardIDs", vocabulary.getAllCardIDsForLanguages(source, target)));
    }

    @GetMapping("/language-options")
    public Map<String, List<String>> languageOptions() {
        Map<String, List<String>> languageOptions = new HashMap<>();
        for (String sourceLanguage: vocabulary.getAllLanguages()) {
            languageOptions.put(sourceLanguage,
                    vocabulary.getTargetLanguagesForSource(sourceLanguage));
        }

        return languageOptions;
    }
}
