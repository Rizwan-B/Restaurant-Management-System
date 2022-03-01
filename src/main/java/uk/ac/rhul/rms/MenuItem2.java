package uk.ac.rhul.rms;

/**
 * A class representing a Menu item entry in the Menu table.
 *
 * @author Tomas Duarte
 */
public class MenuItem2 {
  private int itemId;
  private String item_name;
  private String item_description;
  private String item_category;

  /**
   * The public default constructor for a MenuItem.
   *
   * @param itemId            the int used as a primary key.
   * @param item_name          the String name of the menu item.
   * @param item_description   a String describing the menu item.
   * @param item_category
   */
  public MenuItem2(int itemId, String item_name, String item_description, String item_category) {

    this.itemId = itemId;
    this.item_name = item_name;
    this.item_description = item_description;
    this.item_category = item_category;
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
    return this.item_name;
  }

  /**
   * Getter for the description.
   *
   * @return A String describing the menu item.
   */
  public String getDescription() {
    return this.item_description;
  }

  /**
   * Getter for the description.
   *
   * @return A String describing the menu item.
   */
  public String getCategory() {
    return this.item_category;
  }
  
  /**
   * Creates a string representation of the MenuItem, which is formatted for the SQL Menu table (so
   * you can directly INSERT using the string this returns).
   *
   * @return A string representing the values in the MenuItem
   */
  @Override
  public String toString() {
    String stringForm = this.itemId + ", " + this.item_name + ", " + this.item_description + ", " + this.item_category;
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
      return new MenuItem2(Integer.parseInt(splitValues[0]), splitValues[1], splitValues[2], splitValues[3]);
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
