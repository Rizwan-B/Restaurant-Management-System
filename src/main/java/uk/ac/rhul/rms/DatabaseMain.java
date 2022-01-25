package uk.ac.rhul.rms;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Database to store the data. 
 *
 *@author Muqdas
 *
 */
public class DatabaseMain {
    
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
      CSVReader reader = new CSVReader(new FileReader(
                                   "C:\\Users\\Muqdas\\eclipse-workspace\\TeamProject2022_20\\src\\"
                                      + "main\\resources\\menu.csv"));
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
        createsTable(connect, "menu (food_item varchar(200) NOT NULL,"
            + "food_id varchar(100) NOT NULL,"
            + "food_category varchar(50) NOT NULL,"
            + "food_course varchatr(100) NOT NULL,"
            + "calories varchar(200) NOT NULL,"
            + "food_description varchar(1000) NOT NULL,"
            + "PRIMARY KEY (food_id)"
            + ");");
 
        System.out.println(db.getTables(connect));
      }
     
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
   
}

