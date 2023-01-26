-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 08 Sty 2023, 16:38
-- Wersja serwera: 10.4.27-MariaDB
-- Wersja PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `projectdb`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages_archive`
--

CREATE TABLE `messages_archive` (
  `id` int(11) NOT NULL,
  `reports_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `sender` int(11) NOT NULL,
  `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `messages_archive`
--

INSERT INTO `messages_archive` (`id`, `reports_id`, `date`, `sender`, `message`) VALUES
(1, 1, '2022-12-17 22:54:08', 0, 'test02'),
(2, 1, '2022-12-17 22:54:20', 0, 'test01'),
(3, 1, '2022-12-17 22:59:50', 0, 'test03'),
(4, 3, '2023-01-06 17:30:58', 0, 'wiadmomosc do zarchiwizowania 1'),
(5, 3, '2023-01-06 17:30:58', 0, 'wiadomosc do zarchiwizowania 2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messeges`
--

CREATE TABLE `messeges` (
  `id` int(11) NOT NULL,
  `reports_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `sender` int(11) NOT NULL,
  `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `messeges`
--

INSERT INTO `messeges` (`id`, `reports_id`, `date`, `sender`, `message`) VALUES
(3, 3, '2023-01-06 11:33:10', 0, 'testowa wiadomość\r\ndwulinijkowa'),
(4, 3, '2023-01-06 11:56:42', 1, 'tekstowa\r\nwiadomosc\r\npięcio\r\nlinijkowa\r\nbo tak'),
(5, 3, '2023-01-06 12:55:53', 0, 'to\r\njest\r\nwiadomość\r\nprawie\r\nże\r\ndzieśęcio\r\nlinijkowa\r\nżeby\r\nsprawdzić\r\nczy lista będzie się powiększała a tekst będzie poprawnie się zawijał tak jak powinien ;p, a żeby to sprawdzić musze napisać jeszcze troche tekstu, bo to co bylo napisane nie wystarcza żeby zawinąć tekst ;p'),
(6, 3, '2023-01-06 13:01:18', 1, 'ostatni test'),
(7, 3, '2023-01-06 13:01:18', 0, 'ostatni test'),
(9, 3, '2023-01-06 15:34:36', 0, 'A jednak nie ostatnia');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reports`
--

CREATE TABLE `reports` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `worker_id` int(11) DEFAULT NULL,
  `title` text NOT NULL,
  `status` int(11) DEFAULT NULL,
  `category` text NOT NULL,
  `post_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `priority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `reports`
--

INSERT INTO `reports` (`id`, `user_id`, `worker_id`, `title`, `status`, `category`, `post_date`, `start_date`, `close_date`, `priority`) VALUES
(2, 3, 4, 'tytul aktualny 2', 0, 'testCat2', '2022-12-17 22:59:50', NULL, NULL, 0),
(3, 1, 2, 'test 03', 1, 'cat1', '2023-01-06 10:43:44', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reports_archive`
--

CREATE TABLE `reports_archive` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `worker_id` int(11) NOT NULL,
  `title` text NOT NULL,
  `status` text DEFAULT NULL,
  `category` text NOT NULL,
  `post_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `priority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `reports_archive`
--

INSERT INTO `reports_archive` (`id`, `user_id`, `worker_id`, `title`, `status`, `category`, `post_date`, `start_date`, `close_date`, `priority`) VALUES
(1, 1, 2, 'tytul archiwalny 1', '1', 'testCat', '2022-12-17 22:53:22', '2023-01-08 16:37:29', '2022-12-17 22:53:24', 0),
(2, 3, 4, 'tytul archiwalny 2', '1', 'testCat2', '2022-12-17 22:59:50', '2022-12-17 22:59:50', '2022-12-17 22:59:50', 0),
(3, 1, 2, 'report do zarchiwizowania', '0', 'cat1', '2023-01-06 17:29:48', '2023-01-06 17:29:48', '2023-01-06 17:31:53', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `account_lvl` int(11) NOT NULL,
  `email` text NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `account_lvl`, `email`, `status`) VALUES
(1, 'test01', 'test01', 0, 'example01@example.com', 1),
(2, 'TestUser', 'testUser', 0, 'examplemail', 1),
(3, 'Test02', 'Test02', 1, 'example2Mail', 1),
(4, 'TestUser3', 'testUser3', 0, 'example3mail', 1),
(5, 'Test04', 'Test04', 1, 'example4Mail', 1),
(6, 'registrationTest01', 'registrationTest01', 1, 'registrationTest01', 1),
(7, 'Rgistration01', 'Registration01', 0, 'ex', 1),
(8, 'reg1', 'reg1', 1, 'reg1', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `messages_archive`
--
ALTER TABLE `messages_archive`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reports_id` (`reports_id`);

--
-- Indeksy dla tabeli `messeges`
--
ALTER TABLE `messeges`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reports_id` (`reports_id`);

--
-- Indeksy dla tabeli `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `worker_id` (`worker_id`);

--
-- Indeksy dla tabeli `reports_archive`
--
ALTER TABLE `reports_archive`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`) USING HASH;

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `messages_archive`
--
ALTER TABLE `messages_archive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `messeges`
--
ALTER TABLE `messeges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT dla tabeli `reports`
--
ALTER TABLE `reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT dla tabeli `reports_archive`
--
ALTER TABLE `reports_archive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `messages_archive`
--
ALTER TABLE `messages_archive`
  ADD CONSTRAINT `messages_archive_ibfk_1` FOREIGN KEY (`reports_id`) REFERENCES `reports_archive` (`id`);

--
-- Ograniczenia dla tabeli `messeges`
--
ALTER TABLE `messeges`
  ADD CONSTRAINT `messeges_ibfk_1` FOREIGN KEY (`reports_id`) REFERENCES `reports` (`id`);

--
-- Ograniczenia dla tabeli `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reports_ibfk_2` FOREIGN KEY (`worker_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
