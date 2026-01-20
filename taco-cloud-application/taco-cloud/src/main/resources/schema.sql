DROP TABLE IF EXISTS Taco_Order_Tacos;
DROP TABLE IF EXISTS Taco_Order;
DROP TABLE IF EXISTS Taco_Ingredients;
DROP TABLE IF EXISTS Taco;
DROP TABLE IF EXISTS Ingredient;

CREATE TABLE Ingredient (
                            id VARCHAR(10) NOT NULL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            type VARCHAR(10) NOT NULL
);

CREATE TABLE Taco (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      created_at TIMESTAMP
);

CREATE TABLE Taco_Ingredients (
                                  taco_id BIGINT NOT NULL,
                                  ingredients_id VARCHAR(10) NOT NULL,
                                  PRIMARY KEY (taco_id, ingredients_id),
                                  FOREIGN KEY (taco_id) REFERENCES Taco(id),
                                  FOREIGN KEY (ingredients_id) REFERENCES Ingredient(id)
);

CREATE TABLE Taco_Order (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            street VARCHAR(50) NOT NULL,
                            city VARCHAR(50) NOT NULL,
                            state VARCHAR(50) NOT NULL,
                            zip VARCHAR(10) NOT NULL,
                            cc_number VARCHAR(16),
                            cc_expiration VARCHAR(5),
                            cc_cvv VARCHAR(3),
                            placed_at TIMESTAMP

);

CREATE TABLE Taco_Order_Tacos (
                                  taco_order_id BIGINT NOT NULL,
                                  taco_id BIGINT NOT NULL,
                                  PRIMARY KEY (taco_order_id, taco_id),
                                  FOREIGN KEY (taco_order_id) REFERENCES Taco_Order(id),
                                  FOREIGN KEY (taco_id) REFERENCES Taco(id)
);
