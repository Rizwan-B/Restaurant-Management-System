package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The Controller for the Welcome/Start screen implementing the ControlledScreen Interface.
 *
 * @author Mohamed Yusuf
 *
 */
public class StartScreenController implements ControlledScreen {

  ScreensController screenController;



  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screenController = screenParent;

  }

  @FXML
  private Button menuBtn;

  @FXML
  void changeToMenu(ActionEvent event) {
    this.screenController.setScreen(Main.menuScreenID);
  }
}
