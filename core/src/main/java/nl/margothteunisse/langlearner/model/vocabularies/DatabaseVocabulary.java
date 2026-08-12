package nl.margothteunisse.langlearner.model.vocabularies;

import nl.margothteunisse.langlearner.model.Card;
import nl.margothteunisse.langlearner.model.IVocabulary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("database")
public class DatabaseVocabulary implements IVocabulary {
    private final RowMapper<Card> cardRowMapper = (resultSet, rowNum) -> {
        Card card = new Card(
                resultSet.getString("first_word"),
                resultSet.getString("second_word")
                );
        return card;
        };
    private final JdbcTemplate jdbc;

    public DatabaseVocabulary(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Card getCardByID(int cardID) {
        String sql =
                "SELECT first_word, second_word FROM cards " +
                "WHERE card_id = ? ";
        return jdbc.queryForObject(sql, cardRowMapper, cardID);
    }

    @Override
    public List<Integer> getAllCardIDs() {
        String sql = "SELECT card_id FROM cards";
        return jdbc.queryForList(sql, Integer.class);
    }

    @Override
    public List<Integer> getAllCardIDsForLanguages(String sourceLanguage, String targetLanguage) {
        String sql = "SELECT c.card_id FROM cards c INNER JOIN decks d ON c.deck_id = d.deck_id" +
                " WHERE d.first_language = ? AND d.second_language = ?";
        return jdbc.queryForList(sql, Integer.class, sourceLanguage, targetLanguage);
    }
}
