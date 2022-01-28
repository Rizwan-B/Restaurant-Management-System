package uk.ac.rhul.rms;

/**
 * An enum representing the diets the menu caters for.
 *
 * @author Lucas Kimber
 *
 */
public enum Diet {

  VEGAN("Ve"), VEGETARIAN("V"), NON_VEGETERIAN("N-V"), PEANUT("P"),
  SHELLFISH("S"), WHEAT("G"), DAIRY("D");

  private String dietSymbol;

  Diet(String dietSymbol) {
    this.dietSymbol = dietSymbol;
  }

  /**
   * Returns a shorthand representation of the diet.
   *
   * @return The String representing the diet.
   */
  public String toString() {
    return this.dietSymbol;
  }

  /**
   * Returns a Diet corresponding to the string representation given. 
   *
   * @param diet the string representation of the diet.
   * @return A diet corresponding to the string representation.
   */
  public static Diet toDiet(String diet) {
    switch (diet) {
      case "V":
        return Diet.VEGETARIAN;
      case "Ve":
        return Diet.VEGAN;
      case "N-V":
        return Diet.NON_VEGETERIAN;
      case "P":
        return Diet.PEANUT;
      case "S":
        return Diet.SHELLFISH;
      case "G":
        return Diet.WHEAT;
      case "D":
        return Diet.DAIRY;
      default:
        return null;
    }
  }
}