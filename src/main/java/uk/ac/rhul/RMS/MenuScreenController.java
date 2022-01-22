package uk.ac.rhul.RMS;

import uk.ac.rhul.ScreenManager.ControlledScreen;
import uk.ac.rhul.ScreenManager.ScreensController;

/**
 * @author Lucas Kimber
 *
 */
public class MenuScreenController implements ControlledScreen {

  ScreensController screensController;
  
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }
  
  
}
