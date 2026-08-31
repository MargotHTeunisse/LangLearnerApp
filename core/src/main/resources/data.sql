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