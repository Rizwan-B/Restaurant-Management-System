package uk.ac.rhul.rms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class handles the connection to database.
 *
 * @author Muqdas
 * @author Lucas Kimber
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

  /**
   *
   * @param itemPrimaryKey The primary key of the menu item to be returned from the database.
   * @param connection The connection to the database.
   * @return A menuItem with the values corresponding to the item with the corresponding primary key.
   * @throws InvalidMenuIdException Thrown if the menu item doesn't exist.
   */
  public static MenuItem getMenuItem(String itemPrimaryKey, Connection connection) throws InvalidMenuIdException {
    int itemId;
    String itemName;
    int calories;
    String category;
    Diet dietType;
    String itemDescription;
    String itemImageLocation;
    MenuItem menuItem;

    try {

      ResultSet result = executeQuery(connection, "select * from menu where itemID = '" + itemPrimaryKey + "'");

      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      calories = result.getInt("calories");
      category = result.getString("category");
      dietType = Diet.toDiet(result.getString("diet_type"));
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      menuItem = new MenuItem(itemId, itemName, calories, category, dietType,
          itemDescription, itemImageLocation);

    } catch (SQLException e) {
      throw new InvalidMenuIdException();
    }

    return menuItem;
  }

  public static ArrayList<SeatNumber> getSeatNumber( Connection connection) throws InvalidMenuIdException {
    int tableNumber;
    int seatNumber;
    ArrayList<SeatNumber> seat = new ArrayList<SeatNumber>();
    try {
      ResultSet result = executeQuery(connection, "select * from seat_no;");
      while (result.next()) {
        tableNumber = result.getInt("table_no");
        seatNumber = result.getInt("seat_number");
        SeatNumber st = new SeatNumber(tableNumber,seatNumber);
        seat.add(st);

      }

    } catch(SQLException e){
      System.out.println("This table doesn't exist");
    }

      return seat;
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

  public static ArrayList<MenuItem> getDietType(Connection connection, Diet diet, String cat) throws SQLException {
    ResultSet result = executeQuery(connection, "select * from menu where diet_type= '" + diet.toString() + "' AND category = '" + cat + "';");
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

  public static void cancelOrder(Connection connection, int order_id) throws SQLException{
    Statement st = connection.createStatement();
    st.execute("UPDATE orders_table SET status = 1 WHERE order_id=" + order_id);
  }

  public static void deliverOrder(Connection connection, int order_id) throws SQLException{
    Statement st = connection.createStatement();
    st.execute("UPDATE orders_table SET status = 2 WHERE order_id=" + order_id);
  }

  /**
   * Returns an array list of all the orders currently in the database.
   *
   * @param connection The connection object to the database.
   * @return An ArrayList of all the orders currently in the database.
   * @throws SQLException An exception indicating the database couldn't be queried properly.
   * @throws InvalidMenuIdException An exception indicating one of the menu items in the order couldn't be found.
   */
  public static ArrayList<Order> getOrders(Connection connection) throws SQLException, InvalidMenuIdException {
    ResultSet result = executeQuery(connection, "SELECT * FROM orders_table");
    ArrayList<Order> orders = new ArrayList<>();

    int orderId;
    int tableNo;
    String orders_list;
    Boolean cancelled;

    while (result.next()) {
      orderId = result.getInt("order_id");
      tableNo = result.getInt("table_no");
      orders_list = result.getString("orders_list");
      cancelled = result.getInt("status") == 1;

      Order orderItem = new Order(orderId, tableNo, orders_list, cancelled);
      orders.add(orderItem);
    }
    return orders;
  }

  /**
   * Inserts a confirmed order into the confirmed_orders table.
   *
   * @param connection The connection to the database.
   * @param order The order object being confirmed.
   */
  public static void confirmOrder(Connection connection, ConfirmedOrder order) {
    executeQuery(connection, "INSERT " + String.valueOf(order.getUserId()) + ", " +
        String.valueOf(order.getOrder().getOrderId() + " INTO orders_table"));
  }

  /**
   * Inserts a new order into the orders_table.
   * @param connection The connection to the database.
   * @param table The int for the table_no field.
   * @param list The String of requested menu items for the orders_list field.
   * @throws SQLException An exception thrown when the database can't be queried properly.
   */
  public static void makeTempOrder(Connection connection, int table, String list) throws SQLException {
    connection.createStatement().execute("INSERT INTO orders_table (table_no, orders_list, status) VALUES ('"+table+"' ,'"+list+"' ,'"+-1+"') ;");
  }

  public static String getTempOrder(Connection connection) throws SQLException, InvalidMenuIdException {
    ResultSet result = executeQuery(connection, "SELECT * FROM orders_table");
    String order= result.getString("orders_list");
    DatabaseConnection.getInstance().createStatement().execute("DELETE FROM orders_table WHERE status = -1;");
    return order;
  }
}
