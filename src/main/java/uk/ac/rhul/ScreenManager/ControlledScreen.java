package uk.ac.rhul.ScreenManager;

/**
 * @author Mohamed Yusuf
 * 
 *         An Interface that will be implemented by every class that controls a screen.
 * 
 *         The package code is inspired by the repository in the following link:
 * 
 *         https://github.com/alefbt/javafx-template/blob/master/src/main/java/com/korotkin/JavaFxStart/screen/framework
 *
 */
public interface ControlledScreen {

  /**
   * A public setter method to set the parent for the current screen.
   * 
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  public void setScreenParent(ScreensController screenParent);

}
