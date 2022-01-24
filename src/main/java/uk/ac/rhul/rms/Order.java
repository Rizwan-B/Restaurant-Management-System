package uk.ac.rhul.rms;

import java.util.ArrayList;

/**
 * An object class that holds the entire order of the customer. 
 *
 * @author Rizwan Bagdadi
 */
public class Order {
  
  private ArrayList<Integer> menuItem = new ArrayList<>();
  private int itemId;
  
  public Order(int itemId) {
    this.itemId = itemId;
  }
  
  public int getItemId() {
    return itemId;
  }

  public ArrayList<Integer> getMenuItem() {
    return menuItem;
  }

  /**
   * This adds an item to the customers order.
   *
   * @param itemId The id of the item to add.
   */
  public void addMenuItem(int itemId) {
    menuItem.add(itemId); //Subject to change, to add from menu.
  }
  
  /**
   * This removes the item from the customers order.
   *
   * @param itemId The id of the item.
   */
  public void removeMenuItem(int itemId) {
    for (int i = 0; i < menuItem.size(); i++) {
      if (itemId == menuItem.get(i)) {
        menuItem.remove(itemId);
      }
    }
  }
}
