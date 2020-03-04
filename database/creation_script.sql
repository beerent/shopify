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