
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `nsoc11`
--

-- clean database
DROP TABLE IF EXISTS `commands_actions`, `datas`, `tasks`, `users`, `actions`, `commands`, `logs`;

-- --------------------------------------------------------

--
-- Table structure for `actions`
--

CREATE TABLE IF NOT EXISTS `actions` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `id_actuator` varchar(36) COLLATE utf8_bin NOT NULL,
  `value` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for `commands`
--

CREATE TABLE IF NOT EXISTS `commands` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `category` varchar(30) COLLATE utf8_bin NOT NULL,
  `lock` int(11) NOT NULL,
  `time_out` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for `commands_actions`
--

CREATE TABLE IF NOT EXISTS `commands_actions` (
  `id_command` varchar(36) COLLATE utf8_bin NOT NULL,
  `id_action` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_command`,`id_action`),
  KEY `id_action` (`id_action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for `datas`
--

CREATE TABLE IF NOT EXISTS `datas` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `data_type` varchar(50) COLLATE utf8_bin NOT NULL,
  `role` varchar(100) COLLATE utf8_bin NOT NULL,
  `value` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for `tasks`
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

-- --------------------------------------------------------

--
-- Table structure for `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL,
  `pwd` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for `logs`
--

CREATE TABLE IF NOT EXISTS `logs` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `date` datetime NOT NULL,
  `from` varchar(200) COLLATE utf8_bin NOT NULL,
  `message` text COLLATE utf8_bin NOT NULL,
  `log_level` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



--
-- Constraints
--

--
-- Table constraints for `commands_actions`
--
ALTER TABLE `commands_actions`
  ADD CONSTRAINT `commands_actions_ibfk_1` FOREIGN KEY (`id_command`) REFERENCES `commands` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `commands_actions_ibfk_2` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`) ON DELETE CASCADE;