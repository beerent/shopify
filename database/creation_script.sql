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

CREATE TABLE `shopify`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `ordered` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `order_user_id_fk`
    FOREIGN KEY (`id`)
    REFERENCES `shopify`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `shopify`.`order_product_map` (
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  INDEX `map_order_id_fk_idx` (`order_id` ASC),
  INDEX `map_product_id_fk_idx` (`product_id` ASC),
  UNIQUE INDEX `unique_entries` (`order_id` ASC, `product_id` ASC),
  CONSTRAINT `map_order_id_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `shopify`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `map_product_id_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `shopify`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `shopify`.`products` 
ADD COLUMN `price` DOUBLE NOT NULL AFTER `description`;

ALTER TABLE `shopify`.`order_product_map` 
DROP INDEX `unique_entries` ;

ALTER TABLE `shopify`.`users` 
ADD COLUMN `phone_number` VARCHAR(20) NULL AFTER `email`;

ALTER TABLE `shopify`.`users` 
CHANGE COLUMN `added` `added` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ;

ALTER TABLE `shopify`.`products` 
CHANGE COLUMN `added` `added` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ;

ALTER TABLE `shopify`.`users` 
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC);

ALTER TABLE `shopify`.`products` 
DROP COLUMN `description`;

ALTER TABLE `shopify`.`products` 
ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC);

ALTER TABLE `shopify`.`orders` 
ADD COLUMN `external_order_id` VARCHAR(45) NOT NULL AFTER `id`;

ALTER TABLE `shopify`.`orders` 
ADD UNIQUE INDEX `external_order_id_UNIQUE` (`external_order_id` ASC);


ALTER TABLE `shopify`.`orders` 
DROP FOREIGN KEY `order_user_id_fk`;

ALTER TABLE `shopify`.`orders` 
CHANGE COLUMN `user_id` `user_id` INT(11) NOT NULL ,
ADD INDEX `order_user_id_fk_idx` (`user_id` ASC);
;

ALTER TABLE `shopify`.`orders` 
ADD CONSTRAINT `order_user_id_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `shopify`.`users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `shopify`.`order_product_map` 
ADD COLUMN `id` INT(11) NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (`id`);