package uk.ac.rhul.ScreenManager;

/**
 * @author Mohamed Yusuf
 * 
 * An Interface that will be implemented by every class that controls a screen.
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
