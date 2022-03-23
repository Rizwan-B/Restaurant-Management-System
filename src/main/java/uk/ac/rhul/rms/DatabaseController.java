package uk.ac.rhul.rms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class handles the connection to database.
 *
 * @author Muqdas
 * @author Lucas Kimber
 * @author Tomas Duarte
 */
public class DatabaseController {

  private static DatabaseController instance = null;

  private DatabaseController() {}

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
   * Executes an update through sqlite.
   *
   * @param connect to database.
   * @param query is the update passed into sqlite.
   */
  public static void executeUpdate(Connection connect, String query) {
    try {
      Statement statement = connect.createStatement();
      statement.executeUpdate(query);

    } catch (SQLException e) {
      e.printStackTrace();
    }
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
   * Returns the menu.
   *
   * @throws SQLException SQL Exception.
   */
  public static ArrayList<MenuItem> getMenuItems(Connection connection, String categoryType)
      throws SQLException {
    ResultSet result =
        executeQuery(connection, "select * from menu where category='" + categoryType + "'");
    ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    int itemId;
    String itemName;
    int calories;
    String category;
    Diet dietType;
    String itemDescription;
    String itemImageLocation;
    int itemPrice;
    while (result.next()) {
      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      calories = result.getInt("calories");
      category = result.getString("category");
      dietType = Diet.toDiet(result.getString("diet_type"));
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      itemPrice = result.getInt("item_price");
      MenuItem menuItem = new MenuItem(itemId, itemName, calories, category, dietType,
          itemDescription, itemImageLocation, itemPrice);
      menuItems.add(menuItem);
    }
    return menuItems;
  }

  /**
   * Returns menu item.
   *
   * @throws SQLException SQL Exception.
   */
  public static ArrayList<MenuItem2> getMenu2Items(Connection connection, String categoryType) throws SQLException {
    ResultSet result = executeQuery(connection, "select * from menu");
    ArrayList<MenuItem2> menuItems = new ArrayList<MenuItem2>();
    int itemId;
    String itemName;
    int itemCalories;
    String itemCategory;
    String itemDescription;
    String itemImageLocation;
    int itemPrice;
    MenuItem2 menuItem;
    while (result.next()) {
      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      itemCalories = result.getInt("item_calories");
      itemCategory = result.getString("item_category");
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      itemPrice = result.getInt("item_price");
      menuItem = new MenuItem2(itemId, itemName, itemCalories, itemCategory, itemDescription,
          itemImageLocation, itemPrice);
      menuItems.add(menuItem);
    }
    return menuItems;
  }

  /**
   * Filters menu for allergenic items.
   *
   * @author Tomas Duarte
   * @author Mohamed Javid
   */
  public static ArrayList<MenuItem2> getMenu2ItemsFiltered(Connection connection, String categoryType, String[] allergensArray) throws SQLException {
    String allergens = Arrays.toString(allergensArray);
    int length = allergens.length();
    String allergensFormatted = allergens.substring(1,length-1);
    String query = ("SELECT menu2.itemId, menu2.item_name, menu2.item_calories,"
        + " menu2.item_category, menu2.item_description, menu2.item_image_location, menu2.item_price"
        + " FROM menu2"
        + " WHERE menu2.item_category ='" + categoryType + "'"
        + " EXCEPT"
        + " SELECT menu2.itemId, menu2.item_name, menu2.item_calories, menu2.item_category,"
        + " menu2.item_description, menu2.item_image_location, menu2.item_price"
        + " AS menu2allergens"
        + " FROM menu2"
        + " JOIN dish_ingredients_link"
        + " ON menu2allergens.itemId = dish_ingredients_link.menuItemId"
        + " JOIN ingredients"
        + " ON dish_ingredients_link.ingredientId = ingredients.ingredientId"
        + " JOIN allergy_ingredient_link"
        + " ON ingredients.ingredientId = allergy_ingredient_link.ingredientId"
        + " JOIN allergies"
        + " ON allergy_ingredient_link.allergyId = allergies.allergyId"
        + " WHERE menu2allergens.allergy_name in (" + allergensFormatted + ");");
    ResultSet result = executeQuery(connection, query);
    ArrayList<MenuItem2> menuItems = new ArrayList<MenuItem2>();
    int itemId;
    String itemName;
    int itemCalories;
    String itemCategory;
    String itemDescription;
    String itemImageLocation;
    int itemPrice;
    MenuItem2 menuItem;
    while (result.next()) {
      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      itemCalories = result.getInt("item_calories");
      itemCategory = result.getString("item_category");
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      itemPrice = result.getInt("item_price");
      menuItem = new MenuItem2(itemId, itemName, itemCalories, itemCategory, itemDescription,
          itemImageLocation, itemPrice);
      menuItems.add(menuItem);
    }
    return menuItems;
  }

  /**
   * Gets menu item.
   *
   * @param itemPrimaryKey The primary key of the menu item to be returned from the database.
   * @param connection The connection to the database.
   * @return A menuItem with the values corresponding to the item with the corresponding primary
   *         key.
   * @throws InvalidMenuIdException Thrown if the menu item doesn't exist.
   */
  public static MenuItem2 getMenu2Item(String itemPrimaryKey, Connection connection) throws InvalidMenuIdException {
    int itemId;
    String itemName;
    int itemCalories;
    String itemCategory;
    String itemDescription;
    String itemImageLocation;
    int itemPrice;
    MenuItem2 menuItem;
    try {
      ResultSet result = executeQuery(connection, "select * from menu where itemID = '" + itemPrimaryKey + "'");
      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      itemCalories = result.getInt("item_calories");
      itemCategory = result.getString("item_category");
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      itemPrice = result.getInt("item_price");
      menuItem = new MenuItem2(itemId, itemName, itemCalories, itemCategory, itemDescription,
          itemImageLocation, itemPrice);
    } catch (SQLException e) {
      throw new InvalidMenuIdException();
    }
    return menuItem;
  }
    
  /**
   * Gets menu item.
   *
   * @param itemPrimaryKey The primary key of the menu item to be returned from the database.
   * @param connection The connection to the database.
   * @return A menuItem with the values corresponding to the item with the corresponding primary
   *         key.
   * @throws InvalidMenuIdException Thrown if the menu item doesn't exist.
   */
  public static MenuItem getMenuItem(String itemPrimaryKey, Connection connection)
      throws InvalidMenuIdException {
    int itemId;
    String itemName;
    int calories;
    String category;
    Diet dietType;
    String itemDescription;
    String itemImageLocation;
    int itemPrice;
    MenuItem menuItem;

    try {

      ResultSet result =
          executeQuery(connection, "select * from menu where itemID = '" + itemPrimaryKey + "'");

      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      calories = result.getInt("calories");
      category = result.getString("category");
      dietType = Diet.toDiet(result.getString("diet_type"));
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      itemPrice = result.getInt("item_price");
      menuItem = new MenuItem(itemId, itemName, calories, category, dietType, itemDescription,
          itemImageLocation, itemPrice);

    } catch (SQLException e) {
      throw new InvalidMenuIdException();
    }

    return menuItem;
  }

  /**
   * Returns the seat number.
   *
   * @param connection Connnection.
   * @return seat number.
   * @throws InvalidMenuIdException Invalid Menu ID.
   */
  public static ArrayList<SeatNumber> getSeatNumber(Connection connection)
      throws InvalidMenuIdException {
    int tableNumber;
    int seatNumber;
    ArrayList<SeatNumber> seat = new ArrayList<SeatNumber>();
    try {
      ResultSet result = executeQuery(connection, "select * from seat_no;");
      while (result.next()) {
        tableNumber = result.getInt("table_no");
        seatNumber = result.getInt("seat_number");
        SeatNumber st = new SeatNumber(tableNumber, seatNumber);
        seat.add(st);

      }

    } catch (SQLException e) {
      System.out.println("This table doesn't exist");
    }

    return seat;
  }


  /**
   * Login test.
   *
   * @param connection Connection.
   * @param username Username of user.
   * @param password Password of user.
   * @return returns users role.
   * @throws SQLException SQL Exception.
   */
  public static String loginTest(Connection connection, String username, String password)
      throws SQLException {
    Statement st = connection.createStatement();
    ResultSet role = st.executeQuery("SELECT user_role FROM user_table WHERE user_name='" + username
        + "' AND password='" + password + "'");
    String user_role = "";
    while (role.next()) {
      user_role = role.getString(1);
    }
    return user_role;
  }

  /**
   * Returns diet type of item.
   *
   * @param connection Connection.
   * @param diet Diet.
   * @param cat Category.
   * @return Menu item.
   * @throws SQLException SQL Exception.
   */
  public static ArrayList<MenuItem> getDietType(Connection connection, Diet diet, String cat)
      throws SQLException {
    ResultSet result = executeQuery(connection, "select * from menu where diet_type= '"
        + diet.toString() + "' AND category = '" + cat + "';");
    ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    int itemId;
    String itemName;
    int calories;
    String category;
    Diet dietType;
    String itemDescription;
    String itemImageLocation;
    int itemPrice;
    while (result.next()) {
      itemId = result.getInt("itemID");
      itemName = result.getString("item_name");
      calories = result.getInt("calories");
      category = result.getString("category");
      dietType = Diet.toDiet(result.getString("diet_type"));
      itemDescription = result.getString("item_description");
      itemImageLocation = result.getString("item_image_location");
      itemPrice = result.getInt("item_price");
      MenuItem menuItem = new MenuItem(itemId, itemName, calories, category, dietType,
          itemDescription, itemImageLocation, itemPrice);
      menuItems.add(menuItem);
    }
    return menuItems;
  }

  public static void cancelOrder(Connection connection, int order_id) throws SQLException {
    Statement st = connection.createStatement();
    st.execute("UPDATE orders_table SET status = 1 WHERE order_id=" + order_id);
  }

  public static void deliverOrder(Connection connection, int order_id) throws SQLException {
    Statement st = connection.createStatement();
    st.execute("UPDATE orders_table SET status = 2 WHERE order_id=" + order_id);
  }
  //TODO: Refactor the above two functions to use executeUpdate.

  /**
   * Returns the order.
   *
   * @param connection Connection.
   * @param orderId Order ID.
   * @return the order.
   * @throws InvalidMenuIdException Invalid menu ID.
   * @throws SQLException SQL Exception.
   */
  public static Order getOrder(Connection connection, int orderId)
      throws InvalidMenuIdException, SQLException {
    ResultSet result = executeQuery(connection,
        "SELECT * FROM orders_table WHERE order_id = " + String.valueOf(orderId));

    orderId = result.getInt("order_id");
    int tableNo = result.getInt("table_no");
    String orders_list = result.getString("orders_list");
    Boolean cancelled = result.getInt("status") == 1;

    return new Order(orderId, tableNo, orders_list, cancelled);
  }

  /**
   * Returns an array list of all the orders currently in the database.
   *
   * @param connection The connection object to the database.
   * @return An ArrayList of all the orders currently in the database.
   * @throws SQLException An exception indicating the database couldn't be queried properly.
   * @throws InvalidMenuIdException An exception indicating one of the menu items in the order
   *         couldn't be found.
   */
  public static ArrayList<Order> getOrders(Connection connection)
      throws SQLException, InvalidMenuIdException {
    ResultSet result = executeQuery(connection, "SELECT * FROM orders_table " +
        "LEFT OUTER JOIN " +
        "confirmed_orders ON orders_table.order_id = confirmed_orders.order_id " +
        "WHERE status = 0");
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
    executeUpdate(connection,
        "INSERT INTO confirmed_orders(user_id, order_id) VALUES (" + String.valueOf(order.getUserId())
            + ", " + String.valueOf(order.getOrder().getOrderId()) + ");");
  }



  /**
   * A function for marking a given order as complete.
   *
   * @param connection The connection to the database.
   * @param order The order being marked as complete.
   */
  public static void markOrderComplete(Connection connection, Order order) {
    System.out.println(order.getOrderId());
    executeUpdate(connection, "UPDATE orders_table SET status = 2 WHERE order_id = " +
        order.getOrderId());
  }

  /**
   * Inserts a new temporary order into the orders_table.
   *
   * @param connection The connection to the database.
   * @param table The int for the table_no field.
   * @param list The String of requested menu items for the orders_list field.
   * @throws SQLException An exception thrown when the database can't be queried properly.
   */
  public static void makeTempOrder(Connection connection, int table, String list)
      throws SQLException {
    connection.createStatement()
        .execute("INSERT INTO orders_table (table_no, orders_list, status, quantity) VALUES ('"
            + table + "' ,'" + list + "' ,'" + -1 + "','1') ;");
  }

  /**
   * Gets and deletes the temporary order.
   *
   * @param connection The connection of the database.
   * @return OrderList of the temp order.
   * @throws SQLException An exception thrown when the database can't be properly queried.
   * @throws InvalidMenuIdException An exception thrown when trying to incorrectly access the menu
   *         database
   */
  public static String getTempOrder(Connection connection)
      throws SQLException, InvalidMenuIdException {
    ResultSet result = executeQuery(connection, "SELECT * FROM orders_table WHERE status = -1");
    String order = result.getString("orders_list");
    DatabaseConnection.getInstance().createStatement()
        .execute("DELETE FROM orders_table WHERE status = -1;");
    return order;
  }

  /**
   * A function that returns all the orders a given userId is working on.
   *
   * @param connection The connection to the database.
   * @param userId The userId of the kitchen staff to return the orders of.
   * @return An ArrayList of all the orders being worked on by the given kitchen staff.
   * @throws SQLException Thrown if the query encounters an error.
   * @throws InvalidMenuIdException Thrown if an order is being worked on for a menu item that
   *         doesn't exist.
   */
  public static ArrayList<ConfirmedOrder> getWorkingOnOrders(Connection connection, int userId)
      throws SQLException, InvalidMenuIdException {
    ArrayList<ConfirmedOrder> workingOn = new ArrayList<>();
    ResultSet result = executeQuery(connection,
        "SELECT * FROM confirmed_orders WHERE user_id = " + String.valueOf(userId));

    while (result.next()) {
      workingOn.add(new ConfirmedOrder(getOrder(connection, result.getInt("order_id")), userId));
    }
    return workingOn;
  }

  /**
   * Gets all the orders currently being worked on.
   *
   * @param connection The connection to the database.
   * @return An ArrayList of all the orders currently being worked on.
   * @throws SQLException Thrown if the query encounters an error.
   * @throws InvalidMenuIdException Thrown if an order is being worked on for a menu item that
   * doesn't exist.
   */
  public static ArrayList<Order> getAllWorkingOn(Connection connection)
          throws SQLException, InvalidMenuIdException {
    ArrayList<Order> workingOn = new ArrayList<>();
    ResultSet result = executeQuery(connection,
            "SELECT * FROM confirmed_orders WHERE status = 0");

    while (result.next()) {
      workingOn.add(getOrder(connection, result.getInt("order_id")));
    }
    return workingOn;
  }

  /**
   * Checks the database for the next empty order id.
   *
   * @param connection The connection to the database.
   */
  public static int nextInt(Connection connection)
    throws SQLException{
    int highestInt = 0;
    ResultSet result = executeQuery(connection,"SELECT order_id FROM orders_table;");
    while(result.next()){
      if (result.getInt("order_id") > highestInt){
        highestInt = result.getInt("order_id"); }
    }
    return highestInt+1;
  }

  /**
   * Takes an Order object and inserts it into the database.
   *
   * @param connection The connection to the database.
   * @param order The Order object to be inserted.
   */
  public static void addOrder(Connection connection, Order order) {
    executeUpdate(connection, "INSERT INTO orders_table (order_id,table_no, orders_list, status, quantity) VALUES" +
        " ('" + order.getOrderId() + "', '" + order.getTableNumber() + "', '" + order.getItemIds() + "', 0, 1);");
  }

  public static int getItemId(Connection connection, String itemName) throws SQLException {
    ResultSet result = executeQuery(connection,
            "SELECT itemID FROM menu WHERE item_name='"+ itemName +"'");
    int itemId = result.getInt("itemID");
    return itemId;
  }
}
