INSERT INTO task1.ingredient (ingredient_id,i_name, weight, calories, price, type_id) VALUES
  (1,'cucumber', 4000, 300, 20, 1),
  (2,'potato', 10000, 500, 14, 1),
  (3,'pork', 6000, 1100, 120, 2);

INSERT INTO task1.salad (s_name)
VALUES ('test salad');

INSERT INTO task1.salad_ingredient (salad_id, ingredient_id, weight) VALUES
  (1, 1, 20),
  (1, 2, 40),
  (1, 3, 50);

COMMIT;