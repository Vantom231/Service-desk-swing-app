-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 28 Sty 2023, 10:59
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
(1, 1, '2023-01-28 10:39:17', 0, 'przykładowa wiadomość'),
(2, 1, '2023-01-28 10:44:24', 1, 'Przykłądowa odpowiedz do wątku pierwszego'),
(3, 2, '2023-01-28 10:42:56', 0, 'wiadomość wątku 3 do zarchiwizowania'),
(4, 2, '2023-01-28 10:44:56', 1, 'Przykłądowa odpowiedz do wątku trzeciego'),
(5, 3, '2023-01-28 10:39:52', 0, 'wiadomosc do archiwizacji'),
(6, 3, '2023-01-28 10:45:18', 1, 'Przykłądowa odpowiedz do wątku pierwszego'),
(7, 4, '2023-01-28 10:40:09', 0, 'wiadomość do archiwizacji'),
(8, 4, '2023-01-28 10:46:21', 1, 'Odpowiedz do wątku drugiego'),
(10, 5, '2023-01-28 10:43:28', 0, 'Wiadomość wątku czwartego do zarchiwizowania'),
(11, 5, '2023-01-28 10:46:47', 1, 'Odpowiedz do wątku czwartego');

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
(1, 1, '2023-01-28 10:38:11', 0, 'this is an open thread'),
(2, 2, '2023-01-28 10:38:30', 0, 'this is an open thread 2'),
(4, 4, '2023-01-28 10:39:34', 0, 'przykładowa wiadomość druga'),
(7, 7, '2023-01-28 10:41:45', 0, 'This is an open thread 3'),
(8, 8, '2023-01-28 10:41:58', 0, 'This is an open thread 4'),
(9, 9, '2023-01-28 10:42:20', 0, 'przykłądowa wiadomość wątku trzeciego'),
(10, 10, '2023-01-28 10:42:41', 0, 'przykładowa wiadomość wątku 4'),
(16, 4, '2023-01-28 10:46:12', 1, 'Odpowiedz do wątku drugiego'),
(18, 10, '2023-01-28 10:46:36', 1, 'Odpowiedz do wątku czwartego');

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
(1, 1, NULL, 'Open Thread 1', 0, 'cat1', '2023-01-28 10:38:11', NULL, NULL, 0),
(2, 1, NULL, 'Open Thread 2', 0, 'cat2', '2023-01-28 10:38:29', NULL, NULL, 0),
(4, 1, 4, 'current thread 2', 0, 'cat1', '2023-01-28 10:39:34', '2023-01-28 10:45:48', NULL, 0),
(7, 2, NULL, 'Open thread 3', 0, 'cat2', '2023-01-28 10:41:45', NULL, NULL, 0),
(8, 2, NULL, 'Open Thread 4', 0, 'cat1', '2023-01-28 10:41:58', NULL, NULL, 0),
(9, 2, 3, 'current thread 3', 0, 'cat1', '2023-01-28 10:42:20', '2023-01-28 10:44:02', NULL, 0),
(10, 2, 4, 'Current thread 4', 0, 'cat4', '2023-01-28 10:42:41', '2023-01-28 10:45:53', NULL, 0);

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
(1, 1, 3, 'current thread 1', '0', 'cat1', '2023-01-28 10:39:17', '2023-01-28 10:43:54', '2023-01-28 10:44:27', 0),
(2, 2, 3, 'archive thread 3', '0', 'cat1', '2023-01-28 10:42:56', '2023-01-28 10:44:05', '2023-01-28 10:45:04', 0),
(3, 1, 3, 'archive message 1', '0', 'cat1', '2023-01-28 10:39:52', '2023-01-28 10:43:59', '2023-01-28 10:45:22', 0),
(4, 1, 4, 'archive message 2', '0', 'cat1', '2023-01-28 10:40:09', '2023-01-28 10:45:50', '2023-01-28 10:46:23', 0),
(5, 2, 4, 'archive thread 4', '0', 'cat3', '2023-01-28 10:43:28', '2023-01-28 10:45:55', '2023-01-28 10:46:48', 0),
(6, 2, 4, 'archive thread 4', '0', 'cat3', '2023-01-28 10:43:28', '2023-01-28 10:45:55', '2023-01-28 10:47:28', 0);

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
(1, 'user1', 'user1', 0, 'user1@example.com', 1),
(2, 'user2', 'user2', 0, 'user2@example.com', 1),
(3, 'work1', 'work1', 1, 'work1@example.com', 1),
(4, 'work2', 'work2', 1, 'work2@example.com', 1);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT dla tabeli `messeges`
--
ALTER TABLE `messeges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT dla tabeli `reports`
--
ALTER TABLE `reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `reports_archive`
--
ALTER TABLE `reports_archive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
