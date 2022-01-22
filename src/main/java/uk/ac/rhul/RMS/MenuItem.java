package uk.ac.rhul.RMS;

/**
 * A class representing a Menu item entry in the Menu table.
 *
 * @author Lucas Kimber
 *
 */
public class MenuItem {
  private int itemId;
  private String itemName;
  private int calories;
  private String category;
  private String itemDecription;
  private String itemImageLocation;
  
  /**
   * Getter for the primary key of the Menu Item.
   *
   * @return - the int used as a primary key.
   */
  public int getId() {
    return this.itemId;
  }
  
  /**
   * Getter for the name of the dish.  
   *
   * @return - the String name of the menu item.
   */
  public String getName() {
    return this.itemName;
  }
  
  /**
   * Getter for the number of calories. 
   *
   * @return - an int representing the number of calories it has.
   */
  public int getCalories() {
    return this.calories;
  }
  
  /**
   * Getter for the category. 
   *
   * @return - the String category the item belongs to.
   */
  public String getCategory() {
    return this.category;
  }
  
  /**
   * Getter for the description. 
   *
   * @return - a String describing the menu item.
   */
  public String getDescription() {
    return this.itemDecription;
  }
  
  /**
   * Getter for the image path. 
   *
   * @return - a String filepath for the image of this menu item.
   */
  public String getImageLocation() {
    return this.itemImageLocation;
  }
}
