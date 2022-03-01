package uk.ac.rhul.rms;

/**
 * A class representing a Menu item entry in the Menu table.
 *
 * @author Lucas Kimber
 */
public class MenuItem {
  private int itemId;
  private String itemName;
  private int calories;
  private String category;
  private Diet dietType;
  private String itemDescription;
  private String itemImageLocation;
  private int itemPrice;

  /**
   * The public default constructor for a MenuItem.
   *
   * @param itemId            the int used as a primary key.
   * @param itemName          the String name of the menu item.
   * @param calories          an int representing the number of calories it has.
   * @param category          the String category the item belongs to.
   * @param itemDescription   a String describing the menu item.
   * @param itemImageLocation a String filepath for the image of this menu item.
   * @param itemPrice         the int used as prices for menu item
   */
  public MenuItem(int itemId, String itemName, int calories, String category, Diet dietType,
                  String itemDescription, String itemImageLocation, int itemPrice) {

    this.itemId = itemId;
    this.itemName = itemName;
    this.calories = calories;
    this.category = category;
    this.dietType = dietType;
    this.itemDescription = itemDescription;
    this.itemImageLocation = itemImageLocation;
    this.itemPrice=itemPrice;
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
   * Getter for the number of calories.
   *
   * @return An int representing the number of calories it has.
   */
  public int getCalories() {
    return this.calories;
  }

  /**
   * Getter for the category.
   *
   * @return The String category the item belongs to.
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Getter for the diet type.
   *
   * @return A Diet enum representing the diets the dish is suitable for.
   */
  public Diet getDietType() {
    return this.dietType;
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
   * Getter for the image path.
   *
   * @return A String filepath for the image of this menu item.
   */
  public String getImageLocation() {
    return this.itemImageLocation;
  }


  /**
   * Creates a string representation of the MenuItem, which is formatted for the SQL Menu table (so
   * you can directly INSERT using the string this returns).
   *
   * @return A string representing the values in the MenuItem
   */

  /**
   * Below is a getter method for item price
   * @return
   */
  public int getprice(){
    return this.itemPrice;
  }

  @Override
  public String toString() {
    String stringForm = this.itemId + ", " + this.itemName + ", " + Integer.toString(this.calories)
        + ", " + this.itemDescription + ", " + this.category + ", " + this.dietType.toString()
        + ", " + this.itemDescription + ", " + this.itemImageLocation + ", " + this.itemPrice;
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
  public static MenuItem toMenuItem(String menuItemValues) throws ToMenuItemFormatException {
    String[] splitValues = menuItemValues.split(", ");
    try {
      return new MenuItem(Integer.parseInt(splitValues[0]), splitValues[1],
          Integer.parseInt(splitValues[2]), splitValues[3], Diet.toDiet(splitValues[4]),
          splitValues[5], splitValues[6], Integer.parseInt(splitValues[7]));
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
    if (other instanceof MenuItem) {

      MenuItem otherItem = (MenuItem) other;

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
