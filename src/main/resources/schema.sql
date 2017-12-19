CREATE SCHEMA IF NOT EXISTS task1;

DROP TABLE IF EXISTS task1.salad_ingredient;
DROP TABLE IF EXISTS task1.ingredient;
DROP TABLE IF EXISTS task1.ingredient_type;
DROP TABLE IF EXISTS task1.salad;
DROP TABLE IF EXISTS task1.user_role;
DROP TABLE IF EXISTS task1.users;
DROP TABLE IF EXISTS task1.role;

CREATE TABLE task1.ingredient_type(
  type_id INT PRIMARY KEY ,
  type_name  VARCHAR(50) NOT NULL
);

CREATE TABLE task1.ingredient(
  ingredient_id SERIAL PRIMARY KEY ,
  i_name VARCHAR(50) NOT NULL UNIQUE ,
  weight DOUBLE PRECISION NOT NULL ,
  calories REAL NOT NULL ,
  price REAL NOT NULL,
  fresh BOOL NOT NULL DEFAULT TRUE ,
  i_description TEXT DEFAULT '',
  type_id INT NOT NULL REFERENCES task1.ingredient_type
);

CREATE TABLE task1.salad(
  salad_id SERIAL PRIMARY KEY ,
  s_name VARCHAR(50)  UNIQUE ,
  s_description TEXT DEFAULT ''
);

CREATE TABLE task1.salad_ingredient (
  salad_id      INT NOT NULL REFERENCES task1.salad,
  ingredient_id INT NOT NULL REFERENCES task1.ingredient,
  weight DOUBLE PRECISION NOT NULL,
  UNIQUE (salad_id, ingredient_id)
);

CREATE TABLE users(
  u_id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(45) NOT NULL ,
  email VARCHAR(80) NOT NULL UNIQUE ,
  password VARCHAR(50) NOT NULL
);

CREATE TABLE role(
  role_id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE user_role(
  u_id INT REFERENCES users,
  r_id INT REFERENCES role,
  UNIQUE (u_id, r_id)
);

COMMIT;
