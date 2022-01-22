package uk.ac.rhul.RMS;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import uk.ac.rhul.ScreenManager.ControlledScreen;
import uk.ac.rhul.ScreenManager.ScreensController;

public class StartScreenController implements Initializable, ControlledScreen {
  
  ScreensController screenController;

  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screenController = screenParent;
    
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO I will add things here, please don't touch this -Dr Mo
  }
  

}
