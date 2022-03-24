package uk.ac.rhul.rms;

/**
 * An error to be thrown when trying to cast a string to a menu object using the incorrect format.
 *
 * @author Lucas Kimber
 *
 */
public class ToMenuItemFormatException extends Exception {

  /**
   * The serialVersionUID for Java Exceptions.
   */
  private static final long serialVersionUID = 1L;


  /**
   * Sends the relevant exception message to the exception super class.
   */
  public ToMenuItemFormatException() {
    super("Menu values where not in the correct format to be converted to a menu object.");
  }
}
