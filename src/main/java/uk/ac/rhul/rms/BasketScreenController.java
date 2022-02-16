package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.swing.*;

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

  @FXML
  private Button backBtn;

  @FXML
  private Button getOrderBtn;

  @FXML
  void getOrders(ActionEvent event){

  }
  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }
  
}
