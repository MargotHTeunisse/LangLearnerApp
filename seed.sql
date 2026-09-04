CREATE TABLE decks (
                       deck_id int NOT NULL,
                       first_language varchar(50) NOT NULL,
                       second_language varchar(50) NOT NULL,
                       PRIMARY KEY(deck_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE cards (
                       card_id int NOT NULL AUTO_INCREMENT,
                       first_word varchar(50) NOT NULL,
                       second_word varchar(50) NOT NULL,
                       deck_id int,
                       PRIMARY KEY (card_id),
                       FOREIGN KEY (deck_id) REFERENCES decks(deck_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `decks` (deck_id, first_language, second_language)
VALUES (1, 'EN', 'FI'),
       (2, 'ES', 'FR'),
       (3, 'JP', 'EN'),
       (4, 'FR', 'FI'),
       (5, 'EN', 'ES');
COMMIT;

INSERT INTO `cards` (first_word, second_word, deck_id)
VALUES
    ('cat','kissa', 1),
    ('dog','koira', 1),
    ('bird','lintu', 1),
    ('bear','karhu', 1),
    ('friend', 'ystävä', 1),
    ('gato', 'chat', 2),
    ('perro', 'chien', 2),
    ('ave', 'oiseau', 2),
    ('猫', 'cat', 3),
    ('犬', 'dog', 3),
    ('魚', 'fish', 3),
    ('鳥', 'bird', 3),
    ('loup', 'susi', 4),
    ('jour', 'päivä', 4),
    ('pain', 'leipä', 4),
    ('dog', 'perro', 5),
    ('fish', 'pez', 5);
COMMIT;