-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mer. 20 mai 2020 à 10:58
-- Version du serveur :  10.4.10-MariaDB
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `boosttest`
--

-- --------------------------------------------------------

--
-- Structure de la table `command`
--

DROP TABLE IF EXISTS `command`;
CREATE TABLE IF NOT EXISTS `command` (
  `command_id` bigint(20) NOT NULL,
  `command_type` int(11) DEFAULT NULL,
  `customer_phone` bigint(20) DEFAULT NULL,
  `date_creation` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` float NOT NULL,
  `validator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`command_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `command`
--

INSERT INTO `command` (`command_id`, `command_type`, `customer_phone`, `date_creation`, `status`, `total_price`, `validator`) VALUES
(10, 0, 693836208, '2020-05-19 14:35:40', 'EN_ATTENTE', 40000, 'zouks008'),
(11, 0, 690909090, '2020-05-19 14:36:15', 'EN_ATTENTE', 20000, 'zouks008'),
(12, 0, 690909090, '2020-05-20 05:47:08', 'EN_ATTENTE', 20000, 'zouks008'),
(13, 0, 690909090, '2020-05-20 05:47:29', 'EN_ATTENTE', 20000, 'zouks008'),
(14, 0, 690909090, '2020-05-20 05:47:43', 'EN_ATTENTE', 20000, 'zouks008'),
(15, 0, 690909090, '2020-05-20 06:00:52', 'EN_ATTENTE', 20000, 'zouks008'),
(16, 0, 693836208, '2020-05-20 06:01:52', 'EN_ATTENTE', 20000, 'zouks008'),
(17, 0, 690909090, '2020-05-20 10:06:35', 'EN_ATTENTE', 80000, 'zouks008');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(18),
(18),
(18);

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` bigint(20) NOT NULL,
  `date_creation` datetime DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` float NOT NULL,
  `quantity` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `stock_stock_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `UKqka6vxqdy1dprtqnx9trdd47c` (`title`),
  KEY `FKr4662pon0sqmd7vr554mxeb6g` (`stock_stock_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `product`
--

INSERT INTO `product` (`product_id`, `date_creation`, `owner`, `price`, `quantity`, `status`, `title`, `stock_stock_id`) VALUES
(5, '2020-05-19 13:45:05', 'zouks008', 4000, 10, 'DISPONIBLE', 'porc-epique', NULL),
(4, '2020-05-19 13:43:15', 'zouks008', 4000, 20, 'DISPONIBLE', 'pistache', NULL),
(6, '2020-05-19 13:47:08', 'zouks008', 2000, 30, 'DISPONIBLE', 'plantain', NULL),
(7, '2020-05-19 13:54:10', 'zouks008', 0, 0, 'DISPONIBLE', 'pistache3', NULL),
(8, '2020-05-19 13:58:02', 'zouks008', 4000, 0, 'INSUFFISANT', 'poulet', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `product_command`
--

DROP TABLE IF EXISTS `product_command`;
CREATE TABLE IF NOT EXISTS `product_command` (
  `id_command_product` bigint(20) NOT NULL AUTO_INCREMENT,
  `available_quantity` int(11) NOT NULL,
  `quantity_ordered` int(11) NOT NULL,
  `command_command_id` bigint(20) DEFAULT NULL,
  `product_product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_command_product`),
  KEY `FKr81grfbgirplxyojts0nnylux` (`command_command_id`),
  KEY `FKeejmyq0p54u7ly2rmgrtpy2x7` (`product_product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `product_command`
--

INSERT INTO `product_command` (`id_command_product`, `available_quantity`, `quantity_ordered`, `command_command_id`, `product_product_id`) VALUES
(1, 10, 10, 10, 8),
(2, 5, 5, 11, 8),
(3, 5, 5, 12, 8),
(4, 5, 5, 13, 8),
(5, 5, 5, 14, 8),
(6, 5, 5, 15, 5),
(7, 5, 5, 16, 5),
(8, 10, 10, 17, 5),
(9, 10, 10, 17, 4);

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `stock_id` bigint(20) NOT NULL,
  `date_creation` datetime DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `product_product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `FKrwdkwjf037066qtbpq0pg0h6n` (`product_product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `stock_movement`
--

DROP TABLE IF EXISTS `stock_movement`;
CREATE TABLE IF NOT EXISTS `stock_movement` (
  `movement_id` bigint(20) NOT NULL,
  `is_an_entry` bit(1) DEFAULT NULL,
  `movement_date` datetime DEFAULT NULL,
  `movement_quantity` int(11) NOT NULL,
  PRIMARY KEY (`movement_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `sub_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_status` varchar(255) DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL,
  `validate` bit(1) DEFAULT NULL,
  `validate_message` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`user_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`user_id`, `mail`, `name`, `pass_word`, `phone_number`, `sub_name`, `user_name`, `user_status`, `user_type`, `validate`, `validate_message`) VALUES
(1, 'zouksmodeste48@gmail.com', 'zouks', '{bcrypt}$2a$10$ygctcDEUKGSdVbK/JWylPu6WUB6yjPb2wXYjR.BE9t4zB5VhRnc6S', '+237693836208', 'modeste', 'zouks008', 'SIGNEDIN', 1, b'1', 3913),
(9, 'zouksmodeste48@gmail.com', 'zouks', '{bcrypt}$2a$10$98FgfPrvJLX2jkNcNa8V/O5L9vOnL/3795sYFJvWa3ne6S3gXto1S', '+237693836208', 'modeste', 'zouks', 'AWAITINGVALIDATION', 1, b'0', 6866);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
