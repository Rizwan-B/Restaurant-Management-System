package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.Connection;
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
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("hello");
  }


  @FXML
  private ListView<String> starterList;

  @FXML
  private Button backBtn;

  @FXML
  private Button basketBtn;

  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.setScreen(Main.startScreenID);
  }

  @FXML
  void basketBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.basketScreenID, Main.basketScreenFile);
    this.screensController.setScreen(Main.basketScreenID);
  }



}
