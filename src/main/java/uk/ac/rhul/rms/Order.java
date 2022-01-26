package uk.ac.rhul.rms;

import java.util.ArrayList;

/**
 * An object class that holds the entire order of the customer.
 *
 * @author Rizwan Bagdadi
 */
public class Order {

  private ArrayList<MenuItem> orderList;

  /**
   * A public default constructor for the order object.
   *
   */
  public Order() {
    this.orderList = new ArrayList<MenuItem>();
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
}
