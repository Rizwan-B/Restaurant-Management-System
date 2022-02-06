package uk.ac.rhul.rms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class handles the connection to database.
 *
 * @author Muqdas
 */
public class DatabaseController {

  private static DatabaseController instance = null;

  private DatabaseController() {
  }

  /**
   * Gets the object for the class DatabaseController.
   *
   * @return the instance.
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
   * @param connect to database.
   * @param query   is the query passed into sqlite.
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
   * @param connect gets the connection to the database.
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


  /**
   * something here.
   *
   * @throws SQLException dfjsfad.
   */
  public static ArrayList<MenuItem> getMenuItems(Connection connection, String categoryType) throws SQLException {
    ResultSet result = executeQuery(connection, "select * from menu where category='" + categoryType + "'");
    ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    int itemId;
    String itemName;
    int calories;
    String category;
    Diet dietType;
    String itemDescription;
    String itemImageLocation;
    while (result.next()) {
      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      calories = result.getInt("calories");
      category = result.getString("category");
      dietType = Diet.toDiet(result.getString("diet_type"));
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      MenuItem menuItem = new MenuItem(itemId, itemName, calories, category, dietType,
          itemDescription, itemImageLocation);
      menuItems.add(menuItem);
    }
    return menuItems;
  }

  public static MenuItem getMenuItem(String itemPrimaryKey, Connection connection) throws SQLException {
    int itemId;
    String itemName;
    int calories;
    String category;
    Diet dietType;
    String itemDescription;
    String itemImageLocation;

    ResultSet result = executeQuery(connection, "select * from menu where id = '" + itemPrimaryKey + "'");

    itemId = result.getInt("itemID");
    itemName = result.getString("item_name");
    calories = result.getInt("calories");
    category = result.getString("category");
    dietType = Diet.toDiet(result.getString("diet_type"));
    itemDescription = result.getString("item_description");
    itemImageLocation = result.getString("item_image_location");
    MenuItem menuItem = new MenuItem(itemId, itemName, calories, category, dietType,
        itemDescription, itemImageLocation);

    //TODO: Add try / catch and custom exception

    return menuItem;
  }

  public static void callWaiterOnTable(Connection connection, int tableNumber) throws SQLException {
    Statement st = connection.createStatement();
    st.execute("insert into waiter_call values(NULL, " + tableNumber + ", 0)");
  }

  public static String loginTest(Connection connection, String username, String password) throws SQLException {
    Statement st = connection.createStatement();
    ResultSet role = st.executeQuery("SELECT user_role FROM user_table WHERE user_name='"
        + username + "' AND password='" + password + "'");
    String user_role = "";
    while (role.next()) {
      user_role = role.getString(1);
    }
    return user_role;
  }
}
