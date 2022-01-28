package uk.ac.rhul.rms;

import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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
  private Connection connection;
  
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
    this.connection = DatabaseController.connection();
  }
  
  @FXML
  private ListView<String> starterList = new ListView<String>();
  
  @FXML
  public void addItems() {
    starterList.getItems().add("Hey");
  }
}
