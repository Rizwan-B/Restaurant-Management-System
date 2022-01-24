package uk.ac.rhul.rms;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Database to store the data. 
 *
 *@author Muqdas
 *
 */
public class Database {
  
  /**
   * The main method to for the database. 
   *
   *@param args is the arguments taken from the command line.
   * @throws SQLException if the connection is  not established.
   * @throws FileNotFoundException if the file is not found.
   * @throws IOException if there is an input or output exception.
   */
  public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
    
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Enter your username: ");
      String user = scanner.nextLine();
  
      System.out.println("Enter your password: ");
      String password = scanner.nextLine();
  
  
      String database = "teachdb.cs.rhul.ac.uk";
  
  
      Connection connection = connectToDatabase(user, password, database);
      if (connection != null) {
        System.out.println("SUCCESS");
      } else {
        System.out.println("ERROR: \tFailed to make connection!");
        System.exit(1);
      }
      
 
      dropTables(connection, "menu;");
      createsTable(connection, "menu (food_item varchar(200) NOT NULL,"
          + "food_id varchar(100) NOT NULL,"
          + "food_category varchar(50) NOT NULL,"
          + "calories int NOT NULL,"
          + "food_description varchar(1000) NOT NULL,"
          + "PRIMARY KEY (food_id)"
          + ");");
    }

  }
  
  /**
   * Connects to the database server.
   *
   * @param user
   *
   * @param password
   * 
   * @param database
   * 
   * @return the connections.
   */
  public static Connection connectToDatabase(String user, String password, String database) {
    System.out.println("------ Testing PostgreSQL JDBC Connection ------");
    Connection connection = null;
    try {
      String protocol = "jdbc:postgresql://";
      String dbName = "/CS2855/";
      String fullUrl = protocol + database + dbName + user;
      connection = DriverManager.getConnection(fullUrl, user, password);
    } catch (SQLException e) {
      String errorMsg = e.getMessage();
      if (errorMsg.contains("authentication failed")) {
        System.out.println("----ERROR----");
        System.out.println("\n\tMake sure you are NOT using your university password.\n"
                    + "\tYou need to use the password that was emailed to you!");
      } else {
        System.out.println("Connection failed! Check output console.");
        e.printStackTrace();
      }
    }
    return connection;
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
  public static void dropTables(Connection connection, String table) {
    try {
      Statement st = connection.createStatement();
      st.execute("DROP TABLE IF EXISTS " + table);  //drops table if table already exists
      System.out.println("TABLE DROPPED");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
     
  }
   
}

