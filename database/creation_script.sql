CREATE TABLE `shopify`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `shopify`.`users` 
ADD COLUMN `added` DATETIME NULL AFTER `email`;

CREATE TABLE `shopify`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(300) NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `shopify`.`products` 
ADD COLUMN `added` DATETIME NULL AFTER `description`;

