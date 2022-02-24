package uk.ac.rhul.rms;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Database to store the data. 
 *
 *@author Muqdas
 *@author Mohamed Yusuf
 *@author Lucas Kimber
 *
 */
public class DatabaseMain {
    
  /**
   * A public default constructor for the DatabaseMain class.
   */
  public DatabaseMain() {
    
  }

  
  /**
   * This creates a table in the database server.
   *
   *@param connection connects to the database.
   *@param tableDescription contains the attributes of the table
   */
  public static void createsTable(Connection connection, String tableDescription) {
    try {
      Statement st = connection.createStatement(); //connecting to database
      st.execute("CREATE TABLE IF NOT EXISTS " + tableDescription); 
      System.out.println("TABLE CREATED");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
     
  }
  
  /**
   * This method to delete the table from database. 
   *
   *@param connection connects to the database
   * @param table is the table name used in the method.
   */
  public static void  dropTables(Connection connection, String table) {
    try {
      Statement st = connection.createStatement();
      st.execute("DROP TABLE IF EXISTS " + table);  //drops table if table already exists
      System.out.println("TABLE DROPPED");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
     
  }
  
  /**
   * Inserts values in to the table from the csv file. 
   *
   *@param connection connects to the database.
   * @param table is the name of the table the values are inserted in. Table name must be same as csv file name.
   */
  public static void insertFromFile(Connection connection, String table) {

    try {
      Statement st = connection.createStatement();
      CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\uk\\ac"
              + "\\rhul\\rms\\" + table + ".csv"));
      String[] values;
      while ((values = reader.readNext()) != null) {
        String composedLine = "INSERT INTO " + table + " VALUES (";
        for (int i = 0; i < values.length; i++) {
          if (i < (values.length - 1)) {
            composedLine += "'" + values[i] + "',";
          } else {
            composedLine += "'" + values[i] + "');";
          }
        }
        st.executeUpdate(composedLine);
      }
      System.out.println("VALUES INSERTED");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (CsvValidationException | IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Queries to filter the menu. 
   *
   *@param connect to the database.
   * @param db is an object for the DatabaseController class.
   * @throws SQLException catches SQL exception.
   */
  @SuppressWarnings("static-access")
  
  public static void queries(Connection connect, DatabaseController db) {
    System.out.println("---Starters---");
    ResultSet query1 = db.executeQuery(connect, "SELECT item_name FROM menu "
        + "WHERE category = 'Starters';");
    try {
      while ((query1.next())) {
        System.out.println(query1.getString(1));
      }
      System.out.println("---Main---");
      ResultSet query2 = db.executeQuery(connect, "SELECT item_name FROM menu "
          + "WHERE category = 'Main';");
      while ((query2.next())) {
        System.out.println(query2.getString(1));
      }
      System.out.println("---Dessert---");
      ResultSet query3 = db.executeQuery(connect, "SELECT item_name FROM menu "
          + "WHERE category = 'Dessert';");
      while ((query3.next())) {
        System.out.println(query3.getString(1));
      }
      
      System.out.println("---VEGAN Starters---");
      ResultSet query4 = db.executeQuery(connect, "SELECT item_name FROM menu "
          + "WHERE category = 'Starters' AND diet_type = 'V';");
      while ((query4.next())) {
        System.out.println(query4.getString(1));
      }
      System.out.println("---VEGAN Main---");
      ResultSet query5 = db.executeQuery(connect, "SELECT item_name FROM menu "
          + "WHERE category = 'Main' AND diet_type = 'V';");
      while ((query5.next())) {
        System.out.println(query5.getString(1));
      }
      System.out.println("---VEGAN Dessert---");
      ResultSet query6 = db.executeQuery(connect, "SELECT item_name FROM menu "
          + "WHERE category = 'Dessert' AND diet_type = 'V' ;");
      while ((query6.next())) {
        System.out.println(query6.getString(1));
      }

      System.out.println("\n\n USERS");
      ResultSet query7 = db.executeQuery(connect, "SELECT * FROM user_table");
      while(query7.next()) {
        System.out.println(query7.getString(1) + ", " + query7.getString(2) + ", "
                + query7.getString(3) + ", " + query7.getString(4));
      }

      System.out.println("\n\nORDERS");
      ResultSet query8 = db.executeQuery(connect, "SELECT * FROM orders_table");
      while(query8.next()) {
        System.out.println(query8.getString(1) + ", " + query8.getString(2) + ", "
                + query8.getString(3) + ", " + query8.getString(4) + ", " +  query8.getString(5));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    
  }
  
  
  /**
   * Main methods to run the tables from the database class.
   *
   *@param args gets the argument from commandline.
   */
  @SuppressWarnings("static-access")
  public static void main(String[] args) {
    try {
      DatabaseController db = DatabaseController.getInstance();
      try (Connection connect = DatabaseConnection.getInstance()) {

        dropTables(connect, "menu;");
        createsTable(connect, "menu (itemID int NOT NULL,"
            + "item_name varchar(200) NOT NULL,"
            + "calories varchar(100) NOT NULL,"
            + "category varchar(100) NOT NULL,"
            + "diet_type varchar(200) NOT NULL,"
            + "item_description varchar(1000) NOT NULL,"
            + "item_image_location varchar(1000),"
            + "itemPrice integer(200) NOT NULL,"
            + "PRIMARY KEY (ItemID)"
            + ");");
        
        dropTables(connect, "products;");
        createsTable(connect, "products (productID int NOT NULL,"
            + "product_name varchar(200) NOT NULL,"
            + "product_description varchar(1000) NOT NULL,"
            + "PRIMARY KEY (productID)"
            + ");");
        
        dropTables(connect, "ingredients;");
        createsTable(connect, " ingredients (ingredientID INT NOT NULL,"
            + "ingredient_name VARCHAR(200) NOT NULL,"
            + "PRIMARY KEY (ingredientID)"
            + ");");

        dropTables(connect, "allergies;");
        createsTable(connect, " allergies (allergyID INT NOT NULL,"
            + "allergy_name VARCHAR(200) NOT NULL,"
            + "PRIMARY KEY (allergyID)"
            + ");");
        
        dropTables(connect, "dish_ingredients_link;");
        createsTable(connect, " dish_ingredients_link ("
            + "productID INT,"
            + "ingredientID INT,"
            + "FOREIGN KEY (productID) REFERENCES products(productID),"
            + "FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID),"
            + "PRIMARY KEY (productID, ingredientID)"
            + ");");
        
        dropTables(connect, "allergy_ingredient_link;");
        createsTable(connect, " allergy_ingredient_link ("
            + "allergyID INT,"
            + "ingredientID INT,"
            + "FOREIGN KEY (allergyID) REFERENCES allergies(allergyID),"
            + "FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID),"
            + "PRIMARY KEY (allergyID, ingredientID)"
            + ");");
        
        dropTables(connect, "user_table;");
        createsTable(connect, "user_table(user_id int NOT NULL,"
                              + "user_name varchar(1000) NOT NULL,"
                              + "password varchar(1000) NOT NULL,"
                              + "user_role varchar(1000) NOT NULL,"
                              + "session_id varchar(1000)," // NULL means not logged it.
                              + "busy number(1) NOT NULL," // 1 means yes 0 means no.
                              + "PRIMARY KEY(user_id));");
        dropTables(connect, "reservations;");
        createsTable(connect, "reservations (reservation_id int NOT NULL,"
                                            + "customer_name varchar(1000) NOT NULL,"
                                            + "table_number int NOT NULL,"
                                            + "PRIMARY KEY(reservation_id)"
                                            + ");");
        dropTables(connect, "waiter_call;");

        createsTable(connect, "waiter_call (call_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "table_no int NOT NULL UNIQUE,"
                + "waiter_id int,"
                + "served number(1) NOT NULL" // 1 yes 0 no.
                + ");");

        dropTables(connect, "orders_table");
        createsTable(connect, "orders_table (order_id int," +
                "table_no int NOT NULL," +
                "orders_list varchar(100)," +
                "status number(2) NOT NULL," +  // 0 means in progress, 1 means cancelled, 2 means delivered.
                "quantity int NOT NULL,"+
                "PRIMARY KEY(order_id));");

        dropTables(connect, "seat_no");
        createsTable(connect, "seat_no (table_no int NOT NULL, " +
                "seat_number int NOT NULL, " +
                "PRIMARY KEY(table_no));");

        dropTables(connect, "confirmed_orders");
        createsTable(connect, "confirmed_orders (user_id int NOT NULL, " +
                "order_id int, " +
                "FOREIGN KEY (user_id) REFERENCES user_table(user_id), " +
                "FOREIGN KEY(order_id) REFERENCES orders_table(order_id))");

        insertFromFile(connect, "menu");
        insertFromFile(connect, "user_table");
        insertFromFile(connect, "seat_no");
        insertFromFile(connect, "orders_table");

        queries(connect, db);
      }
     
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
   
}
