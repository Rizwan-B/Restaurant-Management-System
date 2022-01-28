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
   * @param table is the name of the table the values are inserted in.
   */
  public static void insertFromFile(Connection connection, String table) {

    try {
      Statement st = connection.createStatement();
      CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\uk\\ac"
              + "\\rhul\\rms\\menu.csv"));
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
      try (Connection connect = db.connection()) {
        dropTables(connect, "menu;");
        createsTable(connect, "menu (itemID int NOT NULL,"
            + "item_name varchar(200) NOT NULL,"
            + "calories varchar(100) NOT NULL,"
            + "category varchar(100) NOT NULL,"
            + "diet_type varchar(200) NOT NULL,"
            + "item_description varchar(1000) NOT NULL,"
            + "item_image_location varchar(1000),"
            + "PRIMARY KEY (ItemID)"
            + ");");
        insertFromFile(connect, "menu");
        System.out.println("---Starters---");
        queries(connect, db);
      
      }
     
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
   
}





