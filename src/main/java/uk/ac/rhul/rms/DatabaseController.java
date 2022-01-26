package uk.ac.rhul.rms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles the connection to database. 
 *
 *@author Muqdas
 *
 */
public class DatabaseController {
  
  private static DatabaseController instance = null;
  
  private DatabaseController() {}
  
  /**
   * This methods creates a database connection from sqlite server. 
   *
   *@return Connection to run the queries.
   */
  public static Connection connection() {
    String protocol = "jdbc:sqlite://C:\\Users\\Muqdas\\eclipse-workspace\\TeamProject2022_20"
        + "\\src\\main\\resources\\SQLite\\sqlite-tools-win32-x86-3370200\\menudb.db";
    Connection connect = null;
    try {
      connect = DriverManager.getConnection(protocol);
    } catch (SQLException e) { //catches SQL Exception
      String errorMsg = e.getMessage();
      if (errorMsg.contains("authentication failed")) {
        System.out.println("-----ERROR-----");
      } else {
        System.out.println("Connection failed!");
        e.printStackTrace();
      }
    }
    return connect;
  }
  
  /**
   * Gets the object for the class DatabaseController. 
   *
   *@return the instance.
   */
  
  public static DatabaseController getInstance() {
    if (instance == null) {
      instance = new DatabaseController();
    }
    return instance;
  }

  /**
   * Executes queries through sqlite. 
   *
   *@param connect to database.
   * @param query is the query passed into sqlite.
   * @return the result from the query as ResultSet type.
   */
  public static ResultSet executeQuery(Connection connect, String query) {
    ResultSet result = null;
    try {
      Statement statement = connect.createStatement();
      result = statement.executeQuery(query);
      
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return result;
    
  }
  /**
   * gets a list of string with table names. 
   *
   *@param connect gets the connection to the database.
   * @return a list of string.
   */
  
  public static String[] getTables(Connection connect) {
    String[] list = new String[20];
    
    try {
      ResultSet rs = connect.getMetaData().getTables(null, null, null, null);
      while (rs.next()) {
        String tableName = rs.getString("TABLE_NAME");
        for (int i = 0; i < list.length; i++) {
          list[i] = tableName;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;

  }
   
}
