package uk.ac.rhul.rms;

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
  private Diet dietType;
  private String itemDescription;
  private String itemImageLocation;

  /**
   * The public default constructor for a MenuItem.
   *
   * @param itemId the int used as a primary key.
   * @param itemName the String name of the menu item.
   * @param calories an int representing the number of calories it has.
   * @param category the String category the item belongs to.
   * @param itemDescription a String describing the menu item.
   * @param itemImageLocation a String filepath for the image of this menu item.
   */
  public MenuItem(int itemId, String itemName, int calories, String category, Diet dietType,
      String itemDescription, String itemImageLocation) {

    this.itemId = itemId;
    this.itemName = itemName;
    this.calories = calories;
    this.category = category;
    this.dietType = dietType;
    this.itemDescription = itemDescription;
    this.itemImageLocation = itemImageLocation;
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
  @Override
  public String toString() {
    String stringForm = this.itemId + ", " + this.itemName + ", " + Integer.toString(this.calories)
        + ", " + this.itemDescription + ", " + this.category + ", " + this.dietType.toString()
        + ", " + this.itemDescription + ", " + this.itemImageLocation;
    return stringForm;
  }

  /**
   * Converts a string of values into a menu item. Values follow the same order as the menu
   * constructor, and are each separated with ", ".
   *
   * @param menuItemValues A string of all the values a menu item needs.
   * @return A MenuItem representation of the string of values provided.
   */
  public static MenuItem toMenuItem(String menuItemValues) {
    String[] splitValues = menuItemValues.split(", ");
    MenuItem newItem = new MenuItem(Integer.parseInt(splitValues[0]), splitValues[1],
        Integer.parseInt(splitValues[2]), splitValues[3], Diet.toDiet(splitValues[4]),
        splitValues[5], splitValues[6]);
    return newItem;
  }
}
