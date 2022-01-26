package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the basket screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 *  
 */
public class BasketScreenController implements ControlledScreen {

  ScreensController screensController;
  
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }
  
}
