CREATE TABLE `shopify`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `shopify`.`users` 
ADD COLUMN `added` DATETIME NULL AFTER `email`;