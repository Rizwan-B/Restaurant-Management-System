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
  private String itemDecription;
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
  public MenuItem(int itemId, String itemName, int calories, String category,
      Diet dietType, String itemDescription, String itemImageLocation) {
      
    this.itemId = itemId;
    this.itemName = itemName;
    this.calories = calories;
    this.category = category;
    this.dietType = dietType;
    this.itemDecription = itemDescription;
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
    return this.itemDecription;
  }

  /**
   * Getter for the image path.
   *
   * @return A String filepath for the image of this menu item.
   */
  public String getImageLocation() {
    return this.itemImageLocation;
  }
}
