package uk.ac.rhul.rms;

/**
 * A class representing a Menu item entry in the Menu table.
 *
 * @author Tomas Duarte
 */
public class MenuItem2 {
  private int itemId;
  private String itemName;
  private String itemDescription;
  private String itemCategory;
  private int itemCalories;
  private String itemImageLocation;
  private int itemPrice;

  /**
   * The public default constructor for a MenuItem.
   *
   * @param itemId            the int used as a primary key.
   * @param itemName          the String name of the menu item.
   * @param itemDescription   a String describing the menu item.
   * @param itemCategory      a string describing category.
   * @param itemCalories      an int of the item calories.
   * @param itemImageLocation an string address for item image.
   * @param itemPrice an int of the item price.
   */
  public MenuItem2(int itemId, String itemName, String itemDescription, String itemCategory, int itemCalories, String itemImageLocation, int itemPrice) {

    this.itemId = itemId;
    this.itemName = itemName;
    this.itemDescription = itemDescription;
    this.itemCategory = itemCategory;
    this.itemCalories = itemCalories;
    this.itemImageLocation = itemImageLocation;
    this.itemPrice = itemPrice;
  }

  /**
   * Getter for the primary key of the Menu Item.
   *
   * @return The int used as a primary key.
   */
  public int getId() {
    return this.itemId;
  }

  /**
   * Getter for the name of the dish.
   *
   * @return The String name of the menu item.
   */
  public String getName() {
    return this.itemName;
  }

  /**
   * Getter for the description.
   *
   * @return A String describing the menu item.
   */
  public String getDescription() {
    return this.itemDescription;
  }

  /**
   * Getter for the description.
   *
   * @return A String describing the menu item.
   */
  public String getCategory() {
    return this.itemCategory;
  }
  
  /**
   * Getter for calories.
   *
   * @return An int of the number of calories.
   */
  public int getCalories() {
    return this.itemCalories;
  }
  
  /**
   * Getter for the item image location.
   *
   * @return A String address to item image.
   */
  public String getItemImageLocation() {
    return this.itemImageLocation;
  }
  
  /**
   * Getter for the price.
   *
   * @return An int of the price of the menu item.
   */
  public int getPrice() {
    return this.getPrice();
  }
  
  /**
   * Creates a string representation of the MenuItem, which is formatted for the SQL Menu table (so
   * you can directly INSERT using the string this returns).
   *
   * @return A string representing the values in the MenuItem
   */
  @Override
  public String toString() {
    String stringForm = this.itemId + ", " + this.itemName + ", " + this.itemDescription + ", " 
  + this.itemCategory + ", " + this.itemCalories + ", " + this.itemImageLocation + ", " + this.itemPrice;
    return stringForm;
  }

  /**
   * Converts a string of values into a menu item. Values follow the same order as the menu
   * constructor, and are each separated with ", ".
   *
   * @param menuItemValues A string of all the values a menu item needs.
   * @return A MenuItem representation of the string of values provided.
   * @throws ToMenuItemFormatException Thrown when the parameter string menuItemValues is in a bad
   *                                   format.
   */
  public static MenuItem2 toMenuItem(String menuItemValues) throws ToMenuItemFormatException {
    String[] splitValues = menuItemValues.split(", ");
    try {
      return new MenuItem2(Integer.parseInt(splitValues[0]), splitValues[1], splitValues[2],
          splitValues[3], Integer.parseInt(splitValues[4]), splitValues[5], Integer.parseInt(splitValues[6]));
    } catch (Exception e) {
      throw new ToMenuItemFormatException();
    }
  }

  /**
   * Checks the equality of this MenuItem with another based on its ID.
   *
   * @return A boolean indicating if the passed object is equal or not.
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof MenuItem2) {

      MenuItem2 otherItem = (MenuItem2) other;

      return otherItem.getId() == this.getId();
    }
    return false;
  }

  /**
   * Hashcode returns the id of the MenuItem as it is a primary key, and hence a completely unique
   * representation.
   *
   * @return The unique hash of this object, which is its primary key.
   */
  @Override
  public int hashCode() {
    return this.getId();
  }
}
