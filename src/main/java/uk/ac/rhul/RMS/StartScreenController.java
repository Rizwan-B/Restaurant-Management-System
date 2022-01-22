package uk.ac.rhul.RMS;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uk.ac.rhul.ScreenManager.ControlledScreen;
import uk.ac.rhul.ScreenManager.ScreensController;

/**
 * @author Lucas Kimber
 *
 */
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
  
  @FXML
  private Button menuBtn;
  
  @FXML
  void changeToMenu(ActionEvent event) {
    this.screenController.setScreen(Main.menuScreenID);
  }
}
