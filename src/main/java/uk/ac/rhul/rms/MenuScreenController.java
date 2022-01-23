package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the menu screen implementing the ControlledScreen Interface.
 *
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
