        DROP TABLE IF EXISTS menu2;
        CREATE TABLE IF NOT EXISTS menu2 (
          itemId int NOT NULL,
          item_name varchar(200) NOT NULL,
          item_description varchar(1000) NOT NULL,
          item_category varchar(100) NOT NULL,
          PRIMARY KEY (itemId)
        );
        
        DROP TABLE IF EXISTS ingredients;
        CREATE TABLE IF NOT EXISTS ingredients (
          ingredientId INT NOT NULL,
          ingredient_name VARCHAR(200) NOT NULL,
          PRIMARY KEY (ingredientId)
        );

        DROP TABLE IF EXISTS allergies;
        CREATE TABLE IF NOT EXISTS allergies (
          allergyId INT NOT NULL,
          allergy_name VARCHAR(200) NOT NULL,
          PRIMARY KEY (allergyId)
        );
        
        DROP TABLE IF EXISTS dish_ingredients_link;
        CREATE TABLE IF NOT EXISTS dish_ingredients_link (
          menuItemId INT,
          ingredientId INT,
          FOREIGN KEY (menuItemId) REFERENCES menu2(itemId)
            ON UPDATE CASCADE ON DELETE CASCADE,
          FOREIGN KEY (ingredientId) REFERENCES ingredients(ingredientId)
           ON UPDATE CASCADE ON DELETE CASCADE,
          CONSTRAINT dish_ingredient_pk PRIMARY KEY (menuItemId, ingredientId)
        );
        
        DROP TABLE IF EXISTS allergy_ingredient_link;
        CREATE TABLE IF NOT EXISTS allergy_ingredient_link (
          ingredientId INT UNIQUE,
          allergyId INT,
          FOREIGN KEY (ingredientId) REFERENCES ingredients(ingredientId)
           ON UPDATE CASCADE ON DELETE CASCADE,
          FOREIGN KEY (allergyId) REFERENCES allergies(allergyId)
           ON UPDATE CASCADE ON DELETE CASCADE,
          CONSTRAINT allergy_ingredient_pk PRIMARY KEY (ingredientId, allergyId));

	      INSERT INTO menu2(itemId, item_name, item_description, item_category)
	        VALUES (1, 'pizza', 'a pizza', 'MAIN');
	      INSERT INTO menu2(itemId, item_name, item_description, item_category)
		      VALUES (2, 'pasta', 'plate of pasta', 'MAIN');
	      INSERT INTO menu2(itemId, item_name, item_description, item_category)
		      VALUES (3, 'steak', 'a steak', 'MAIN');
	      INSERT INTO menu2(itemId, item_name, item_description, item_category)
		      VALUES (4, 'ice-cream', 'ice-cream', 'DESSERT');
        
        
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (1, 'dough');
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (2, 'sauce');
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (3, 'cheese');
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (4, 'pasta');
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (5, 'beef');
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (6, 'sugar');
        INSERT INTO ingredients(ingredientId, ingredient_name) VALUES (7, 'milk');

        
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (1, 1);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (1, 2);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (1, 3);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (2, 2);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (2, 3);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (3, 5);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (4, 6);
        INSERT INTO dish_ingredients_link(menuItemId, ingredientId) VALUES (4, 7);
        
        INSERT INTO allergies(allergyId, allergy_name) VALUES (1, 'gluten');
        INSERT INTO allergies(allergyId, allergy_name) VALUES (2, 'dairy');

		    INSERT INTO allergy_ingredient_link(ingredientId, allergyId) VALUES (1, 1);
		    INSERT INTO allergy_ingredient_link(ingredientId, allergyId) VALUES (3, 2);
		    INSERT INTO allergy_ingredient_link(ingredientId, allergyId) VALUES (7, 2);


        SELECT * FROM ingredients
          JOIN allergy_ingredient_link
          ON ingredients.ingredientId = allergy_ingredient_link.ingredientId
          JOIN allergies
          ON allergy_ingredient_link.allergyId = allergies.allergyId;

        SELECT DISTINCT menu2.item_name, menu2.item_description 
        	FROM menu2 
        		JOIN dish_ingredients_link
        		ON menu2.itemId = dish_ingredients_link.menuItemId
      			JOIN ingredients 
      			ON dish_ingredients_link.ingredientId = ingredients.ingredientId;

        -- SELECT * 
        -- 	FROM menu2 
        -- 		JOIN dish_ingredients_link
        -- 		ON menu2.itemId = dish_ingredients_link.menuItemId
      		-- 	JOIN ingredients 
      		-- 	ON dish_ingredients_link.ingredientId = ingredients.ingredientId;
      		-- WHERE NOT EXISTS
        -- 		(SELECT * FROM ingredients
        --   			JOIN allergy_ingredient_link
        --   			ON ingredients.ingredientId = allergy_ingredient_link.ingredientId
        --   			JOIN allergies
        --   			ON allergy_ingredient_link.allergyId = allergies.allergyId)
        -- GROUP BY menu2.itemId;
        
        SELECT *
        FROM allergies;
        
        SELECT allergy_ingredient_link.ingredientId, allergies.allergy_name
        FROM allergy_ingredient_link
        JOIN allergies
        ON allergy_ingredient_link.allergyId = allergies.allergyId;
        
        
        SELECT menu2.itemId, menu2.item_name, menu2.item_description, dish_ingredients_link.ingredientId, ingredients.ingredient_name --, allergy_ingredient_link.allergyId
        FROM menu2
        JOIN dish_ingredients_link
        ON menu2.itemId = dish_ingredients_link.menuItemId
        JOIN ingredients
        ON dish_ingredients_link.ingredientId = ingredients.ingredientId;
        -- JOIN allergy_ingredient_link
        -- ON ingredients.ingredientId = allergy_ingredient_link.allergyId
      
        SELECT menu2.item_name, menu2.item_description
        FROM menu2
        EXCEPT
        SELECT menu2.item_name, menu2.item_description
        FROM menu2
        JOIN dish_ingredients_link
        ON menu2.itemId = dish_ingredients_link.menuItemId
        JOIN ingredients
        ON dish_ingredients_link.ingredientId = ingredients.ingredientId
        JOIN allergy_ingredient_link
        ON ingredients.ingredientId = allergy_ingredient_link.allergyId;

        
        
        
        
        
        
