INSERT INTO `senior` (`id`, `username`, `email`, `create_time`, `city`) VALUES
(1, 'Pepa', 'test@test.cz', '2020-04-18 18:20:57', 'Praha'),
(2, 'Jan Hlozek', 'hlozek@ema.com', '2020-04-28 21:53:49', 'Praha'),
(3, 'Karel', 'ss@ss.cm', '2020-04-28 21:54:49', 'Zlin'),
(4, 'Novotna Marie', 'das@ji.c', '2020-04-28 21:56:30', 'Albertov'),
(5, 'Lukas Hron', 'hron@see.cz', '2020-04-28 21:57:32', 'Pce'),
(6, 'Jan novak', 'stt@sss.cz', '2020-05-04 13:36:56', 'Prague'),
(7, 'Ludek Svoboda', 'test@svob.mm', '2020-05-06 13:27:34', 'Chotebor'),
(8, 'Helmut Marko', 'helmut@ss.oo', '2020-05-06 13:40:34', 'Liben'),
(9, 'Marie Nová', 'nova@mm.vt', '2020-05-07 10:50:45', 'Brno'),
(10, 'Lukas Kral', 'lukasino22@seznam.cz', '2020-05-07 18:23:35', 'Prelouc'),
(11, 'Anthon Ruml', 'lukasino22@seznam.cz', '2020-05-07 18:29:53', 'Pardubice'),
(12, 'Jon On', 'lukasino22@seznam.cz', '2020-05-07 18:31:03', 'Prague'),
(13, 'Ahmed Morta', 'lukasino22@seznam.cz', '2020-05-07 18:46:03', 'Brno'),
(14, 'Jan Jankov', 'lukasino22@seznam.cz', '2020-05-10 17:18:58', 'Berlin'),
(15, 'Shon Defant', 'lukasino22@seznam.cz', '2020-05-10 17:20:23', 'Warsava');

-- --------------------------------------------------------

INSERT INTO `user` (`id`, `username`, `email`, `password`, `create_time`) VALUES
(1, 'novak', 'novak@szn.cz', '$2a$10$qbLjkQCFE8HSldZ/pB18rujxtRsBAgRnEknEcd13RPrPaH4V2fxnO', '2020-04-18 15:24:28'),
(2, 'dvorak', 'dvorak@gg.cz', '$2a$10$qbLjkQCFE8HSldZ/pB18rujxtRsBAgRnEknEcd13RPrPaH4V2fxnO', '2020-04-18 15:24:28'),
(3, 'port', 'test@test.cz', '$2a$10$qbLjkQCFE8HSldZ/pB18rujxtRsBAgRnEknEcd13RPrPaH4V2fxnO', '2020-04-18 18:20:57'),
(4, 'Jon', 'test3@rpn.com', '$2a$10$qbLjkQCFE8HSldZ/pB18rujxtRsBAgRnEknEcd13RPrPaH4V2fxnO', '2020-04-23 18:42:19'),
(5, 'tesact', 'lol@kii.cz', '$2a$10$ZVA/4udCHtNnsdoUiEnrVeSZvuemoVLR5Jz1WpYNjOc4QhdZFGXwO', '2020-05-04 16:49:36'),
(6, 'lukas', 'lukas@test.cz', '$2a$10$qbLjkQCFE8HSldZ/pB18rujxtRsBAgRnEknEcd13RPrPaH4V2fxnO', '2020-05-04 17:11:58');

-- --------------------------------------------------------

INSERT INTO `cart` (`idcart`, `time`, `time_done`, `done`, `user_id`, `senior_id`) VALUES
(1, '2020-04-18 18:20:57','2020-04-19 15:10:33', 1, 3, 1),
(2, '2020-04-19 21:53:49','2020-04-20 09:20:25', 1, 6, 2),
(3, '2020-04-19 21:54:49','2020-04-20 12:25:20', 1, 6, 3),
(4, '2020-04-22 21:56:30','2020-04-23 09:26:10', 1, 2, 4),
(5, '2020-04-23 21:57:32',NULL, 0, 2, 5),
(6, '2020-04-25 13:36:56',NULL, 0, 6, 6),
(7, '2020-04-26 13:27:34','2020-04-27 11:36:15', 1, 1, 7),
(8, '2020-05-01 13:40:34',NULL, 0, 5, 8),
(9, '2020-05-02 10:50:45','2020-05-05 19:45:57', 1, 5, 9),
(10, '2020-05-03 18:23:36','2020-05-10 09:20:50', 1, 3, 10),
(11, '2020-05-04 18:29:53',NULL, 0, 4, 11),
(12, '2020-05-05 18:31:04','2020-05-07 12:21:45', 1, 6, 12),
(13, '2020-05-06 18:46:03',NULL, 0, 6, 13),
(14, '2020-05-08 17:18:58',NULL, 0, NULL, 14),
(15, '2020-05-10 17:20:23',NULL, 0, NULL, 15);

-- --------------------------------------------------------

INSERT INTO `cartitem` (`id`, `item`, `cart_id`) VALUES
(1, 'das', 4),
(2, 'dsa', 4),
(3, 'rum', 5),
(4, 'rohlik 6x', 5),
(5, 'maso', 5),
(6, 'pečivo', 6),
(7, 'mléko', 6),
(8, 'pecivo', 7),
(9, 'mineralka', 7),
(10, 'cheese', 8),
(11, 'whine', 8),
(12, 'apples', 9),
(13, 'pečivo', 9),
(14, 'chicken mean', 10),
(15, 'whine', 10),
(16, 'bear', 10),
(17, 'bread', 10),
(18, 'bread', 11),
(19, 'milk', 11),
(20, 'apples', 11),
(21, 'some alcohol', 11),
(22, 'mineral water', 12),
(23, 'just some whiskey', 13),
(24, 'sádlo', 1),
(25, 'džus', 1),
(26, 'chleba', 1),
(27, 'mouka hladka', 2),
(28, 'vajíčka', 2),
(29, 'salám', 2),
(30, 'pivo', 3),
(31, 'paprika', 3),
(32, 'sádlo', 4),
(33, 'džus', 4),
(34, 'chleba', 13),
(35, 'mouka hladka', 9),
(36, 'vajíčka', 9),
(37, 'salám', 2),
(38, 'pivo', 6),
(39, 'paprika', 6),
(40, 'vine', 14),
(41, 'bread', 14),
(42, 'flour', 14),
(43, 'milk', 15),
(44, 'bread', 15),
(45, 'cheese', 15);