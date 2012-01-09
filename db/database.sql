-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: 127.0.0.1
-- Généré le : Lun 09 Janvier 2012 à 11:30
-- Version du serveur: 5.5.16
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `nsoc11`
--

-- --------------------------------------------------------

--
-- Structure de la table `actions`
--

CREATE TABLE IF NOT EXISTS `actions` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `id_actuator` varchar(36) COLLATE utf8_bin NOT NULL,
  `value` double NOT NULL,
  `time_out` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `lock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `commands`
--

CREATE TABLE IF NOT EXISTS `commands` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `id_categorie` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_categorie` (`id_categorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `commands_actions`
--

CREATE TABLE IF NOT EXISTS `commands_actions` (
  `id_command` varchar(36) COLLATE utf8_bin NOT NULL,
  `id_action` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_command`,`id_action`),
  KEY `id_action` (`id_action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `datas`
--

CREATE TABLE IF NOT EXISTS `datas` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `data_type` varchar(50) COLLATE utf8_bin NOT NULL,
  `role` varchar(100) COLLATE utf8_bin NOT NULL,
  `value` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `datas`
--

INSERT INTO `datas` (`id`, `data_type`, `role`, `value`, `date`) VALUES
('de986bd1-9391-4560-b5b0-1af67ab8e5e1', 'TEMPERATURE', 'temp-int-salle930', 19.6, '2012-01-09 10:00:30');

-- --------------------------------------------------------

--
-- Structure de la table `tasks`
--

CREATE TABLE IF NOT EXISTS `tasks` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin NOT NULL,
  `create_date` datetime NOT NULL,
  `expire_date` datetime NOT NULL,
  `taskstate` varchar(20) COLLATE utf8_bin NOT NULL,
  `script` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `tasks`
--

INSERT INTO `tasks` (`id`, `description`, `create_date`, `expire_date`, `taskstate`, `script`) VALUES
('b33d20bd-2faf-11e1-81b8-0021cc4198bb', 'un deuxième test', '2011-12-26 04:29:16', '2012-01-08 15:11:00', 'WAITING', 'le script !!'),
('e1f4f0a9-2d56-11e1-8e5b-0021cc4198bb', 'essai task update', '2011-12-23 01:23:05', '2012-01-08 10:38:11', 'WAITING', 'le script !! ...');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL,
  `pwd` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `commands`
--
ALTER TABLE `commands`
  ADD CONSTRAINT `commands_ibfk_1` FOREIGN KEY (`id_categorie`) REFERENCES `categories` (`id`);

--
-- Contraintes pour la table `commands_actions`
--
ALTER TABLE `commands_actions`
  ADD CONSTRAINT `commands_actions_ibfk_1` FOREIGN KEY (`id_command`) REFERENCES `commands` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `commands_actions_ibfk_2` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
