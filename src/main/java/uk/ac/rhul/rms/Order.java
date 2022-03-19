package uk.ac.rhul.rms;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * An object class that holds the entire order of the customer.
 *
 * @author Rizwan Bagdadi
 * @author Lucas Kimber
 */
public class Order {

  private final String itemIds;
  private int orderId;
  private int tableNumber;
  private ArrayList<MenuItem> orderList;
  private boolean orderCompletedStatus = false;

  /**
   * The constructor for the Order class, taking the needed information to construct an order
   * object.
   *
   * @param orderId The ID of the order that acts as the primary key in the database.
   * @param tableNumber The table number the order is for.
   * @param itemsList A comma separated list of MenuItem IDs constituting the order.
   * @param cancelled A boolean representing whether the order has been cancelled or not.
   * @throws InvalidMenuIdException An exception that if thrown is likely due to an incorrect
   *         itemsList being passed.
   */
  public Order(int orderId, int tableNumber, String itemsList, boolean cancelled)
      throws InvalidMenuIdException {
    this.orderId = orderId;
    this.tableNumber = tableNumber;
    this.itemIds = itemsList;
    this.orderList = this.parseOrderList(itemsList);
    this.orderCompletedStatus = cancelled;
  }

  /**
   * A public getter method to return the table number of the order
   * @return tableNumber the table number of the order.
   */
  public int getTableNumber() {return tableNumber;}

  /**
   * A public getter method to return the customers order.
   *
   * @return The customers order as an ArrayList.
   */
  public ArrayList<MenuItem> getOrderItems() {
    return this.orderList;
  }

  /**
   * A public getter method to return the status of a customer's order.
   *
   * @return The status of the order as a boolean.
   */
  public boolean getOrderStatus() {
    return this.orderCompletedStatus;
  }

  /**
   * A public getter method to return the itemId of a customer's order.
   *
   * @return The item Ids of the order as a String.
   */
  public String getItemIds() { return this.itemIds; }

  /**
   * A public method to change the status of a customer's order.
   */
  public void changeOrderStatus() {
    this.orderCompletedStatus = !this.orderCompletedStatus;
  }

  /**
   * This adds an item to the customers order.
   *
   * @param menuItem The menu Item to add to the order.
   */
  public void addMenuItem(MenuItem menuItem) {
    this.orderList.add(menuItem);
  }

  /**
   * This removes the item from the customers order.
   *
   * @param menuItemId The id of the item to remove.
   */
  public void removeMenuItem(int menuItemId) {
    for (int i = 0; i < this.orderList.size(); i++) {
      if (this.orderList.get(i).getId() == menuItemId) {
        this.orderList.remove(i);
      }
    }
  }

  /**
   * Takes a list of menu item IDs and returns an ArrayList of MenuItem objects.
   *
   * @param itemListString A string containing the item IDs of an order.
   * @return An ArrayList containing all the MenuItems of the order.
   * @throws InvalidMenuIdException Thrown if one of the menu items in the order doesn't exist.
   */
  private ArrayList<MenuItem> parseOrderList(String itemListString) throws InvalidMenuIdException {
    String[] itemIds = itemListString.split("-");
    ArrayList<MenuItem> menuItems = new ArrayList<>();
    Connection connection = DatabaseConnection.getInstance();

    for (String itemId : itemIds) {
      menuItems.add(DatabaseController.getMenuItem(itemId, connection));
    }
    return menuItems;
  }

  /**
   * Returns a string of all the menu items in the order.
   *
   * @return A string of all the menu items.
   */
  public String toString() {
    String orderString = String.valueOf(this.orderId) + " - ";

    for (MenuItem item : this.orderList) {
      orderString += item.getName() + " ";
    }

    return orderString;
  }

  /**
   * A getter for the orderId corresponding to the orders table.
   *
   * @return An int orderId.
   */
  public int getOrderId() {
    return this.orderId;
  }
}
