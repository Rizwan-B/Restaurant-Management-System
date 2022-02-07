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

  private ArrayList<MenuItem> orderList;
  private boolean orderCompletedStatus = false;

  /**
   * A public default constructor for the order object.
   */
  public Order() {
    this.orderList = new ArrayList<>();
  }

  /**
   * A public getter method to return the customers order.
   *
   * @return The customers order as an ArrayList.
   */
  public ArrayList<MenuItem> getOrder() {
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
    String[] itemIds = itemListString.split(",");
    ArrayList<MenuItem> menuItems = new ArrayList<>();
    Connection connection = DatabaseConnection.getInstance();

    for (String itemId : itemIds) {
      menuItems.add(DatabaseController.getMenuItem(itemId, connection));
    }
    return menuItems;
  }
}
