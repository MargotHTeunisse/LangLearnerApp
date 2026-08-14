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
    private final int size;

    public DatabaseVocabulary(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.size = getAllCardIDs().size();
    }

    @Override
    public Card getCardByID(int cardID) {
        if (cardID <= size) {
            String sql =
                    "SELECT first_word, second_word FROM cards " +
                            "WHERE card_id = ? ";
            return jdbc.queryForObject(sql, cardRowMapper, cardID);
        }
        else {
            String sql =
                    "SELECT second_word as first_word, first_word as second_word FROM cards " +
                            "WHERE card_id = ?";
            return jdbc.queryForObject(sql, cardRowMapper, cardID-size);
        }
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
        List<Integer> cardIds = jdbc.queryForList(sql, Integer.class, sourceLanguage, targetLanguage);

        sql = "SELECT c.card_id + ? FROM cards c INNER JOIN decks d ON c.deck_id = d.deck_id" +
                " WHERE d.first_language = ? AND d.second_language = ?";
        cardIds.addAll(jdbc.queryForList(sql, Integer.class, size, targetLanguage, sourceLanguage));
        return cardIds;
    }

    @Override
    public List<String> getAllLanguages() {
        String sql = "SELECT first_language FROM decks " +
                "UNION SELECT second_language FROM decks";

        return jdbc.queryForList(sql, String.class);
    }

    @Override
    public List<String> getTargetLanguagesForSource(String sourceLanguage) {
        String sql = "SELECT second_language FROM decks WHERE first_language = ? " +
                "UNION SELECT first_language FROM decks WHERE second_language = ? ";

        return jdbc.queryForList(sql, String.class, sourceLanguage, sourceLanguage);
    }
}
