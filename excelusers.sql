-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.15-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for systechtraing
CREATE DATABASE IF NOT EXISTS `systechtraing` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `systechtraing`;

-- Dumping structure for table systechtraing.excelusers
CREATE TABLE IF NOT EXISTS `excelusers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL DEFAULT 0,
  `town` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table systechtraing.excelusers: ~0 rows (approximately)
DELETE FROM `excelusers`;
/*!40000 ALTER TABLE `excelusers` DISABLE KEYS */;
INSERT INTO `excelusers` (`id`, `name`, `age`, `town`) VALUES
	(1, 'John Doe', 23, 'Nairobi'),
	(2, 'Tom Cat', 45, 'Kisumu'),
	(3, 'Jack Daniels', 34, 'Mombasa');
/*!40000 ALTER TABLE `excelusers` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
