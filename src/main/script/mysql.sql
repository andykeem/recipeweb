CREATE DATABASE recipe_dev;
CREATE DATABASE recipe_prod;

CREATE USER `dev`@`localhost` IDENTIFIED BY 'pwdev';
CREATE USER `prod`@`localhost` IDENTIFIED BY 'pwprod';

--GRANT ALL ON db1.* TO 'jeff'@'localhost';
GRANT DELETE ON recipe_dev.* TO 'dev'@'localhost';
GRANT INSERT ON recipe_dev.* TO 'dev'@'localhost';
GRANT SELECT ON recipe_dev.* TO 'dev'@'localhost';
GRANT UPDATE ON recipe_dev.* TO 'dev'@'localhost';

GRANT DELETE ON recipe_prod.* TO 'prod'@'localhost';
GRANT INSERT ON recipe_prod.* TO 'prod'@'localhost';
GRANT SELECT ON recipe_prod.* TO 'prod'@'localhost';
GRANT UPDATE ON recipe_prod.* TO 'prod'@'localhost';