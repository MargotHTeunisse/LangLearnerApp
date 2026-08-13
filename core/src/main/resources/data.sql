INSERT INTO `decks` (deck_id, first_language, second_language)
VALUES (1, 'EN', 'FI'),
(2, 'ES', 'FR');
COMMIT;

INSERT INTO `cards` (first_word, second_word, deck_id)
       VALUES
('cat','kissa', 1),
('dog','koira', 1),
('bird','lintu', 1),
('bear','karhu', 1),
('gato', 'chat', 2),
('perro', 'chien', 2),
('ave', 'oiseau', 2);
COMMIT;