package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the menu screen implementing the ControlledScreen Interface.
 *
 * @author Lucas Kimber
 *
 */
public class MenuScreenController implements ControlledScreen, Initializable {

  ScreensController screensController;

  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;

    this.connection = DatabaseController.connection();
  }
  
  @FXML
  private Button backBtn;
  @FXML
  private Button callWaitor;
  @FXML
  private Button basket;
  
  @FXML
  void backToStart(ActionEvent event) {
    this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
    this.screensController.setScreen(Main.startScreenID);

  }

  @FXML
  private ListView<String> starterList = new ListView<String>();

  @FXML
  private void addStarterItems() throws SQLException {

  }
  
  void goToBasket(ActionEvent event) {
    //For whoever to add to
  }
  
  void callTheWaitor(ActionEvent event) {
    
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      ArrayList<MenuItem> menuStarters = DatabaseController.getMenuStarters();
      for (MenuItem starter : menuStarters) {
        // starterList.getItems().add(starter.toString());
        System.out.println(starter.toString());
      }
    } catch (Exception e) {
      // hehah
    }


  }
}
