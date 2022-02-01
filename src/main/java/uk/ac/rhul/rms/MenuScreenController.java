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
  private DatabaseController dbController;
  private Connection dbConnection;

  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
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
    dbController = DatabaseController.getInstance();
    dbConnection = DatabaseConnection.getInstance();

    try {
      ArrayList<MenuItem> starters = DatabaseController.getMenuItems(dbConnection,"Starters");
    } catch (Exception e) {
      System.out.println("error");
    }



  }
}
