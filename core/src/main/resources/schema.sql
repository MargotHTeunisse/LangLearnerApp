CREATE TABLE decks (
    deck_id int NOT NULL AUTO_INCREMENT,
    first_language varchar(50) NOT NULL,
    second_language varchar(50) NOT NULL,
    PRIMARY KEY(deck_id)
);

CREATE TABLE cards (
                         card_id int NOT NULL AUTO_INCREMENT,
                         first_word varchar(50) NOT NULL,
                         second_word varchar(50) NOT NULL,
                         deck_id int,
                         PRIMARY KEY (card_id),
                         FOREIGN KEY (deck_id) REFERENCES decks(deck_id)
);