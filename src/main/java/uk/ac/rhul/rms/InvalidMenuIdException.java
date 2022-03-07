package uk.ac.rhul.rms;

/**
 * An exception that is thrown when an attempt is made to get a menu item using a non-existent
 * primary key.
 *
 * @author Lucas Kimber
 */
public class InvalidMenuIdException extends Exception {

  /**
   * The serialVersionUID for Java Exceptions.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Sends the relevant exception message to the exception super class.
   */
  public InvalidMenuIdException() {
    super("An attempt has been made to get a MenuItem "
        + "with an ID that does not exist in the database.");
  }
}
